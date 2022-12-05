package dev.dracarys.com.hospitalquerysystem.controller;


import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.*;
import dev.dracarys.com.hospitalquerysystem.service.AppointmentServices;
import dev.dracarys.com.hospitalquerysystem.service.DoctorServices;
import dev.dracarys.com.hospitalquerysystem.service.PatientServices;
import dev.dracarys.com.hospitalquerysystem.util.ConvertLocalDateToDateType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/hospital/appointments")
@Log4j2
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentServices appointmentsServices;

    private final DoctorServices doctorServices;

    private final PatientServices patientServices;

    @GetMapping("/list")
    @Operation(summary = "List all appointments", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / DOCTOR / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success list all appointments"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<List<AppointmentGetReturnObject>> findAll() {
        return new ResponseEntity<>(appointmentsServices.listAllAppointments(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Create new appointment", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success created new appointment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentPostRequestBody.class))}),
            @ApiResponse(responseCode = "409", description = "Already exists appointment"),
            @ApiResponse(responseCode = "400", description = "if doctor or patient not exist"),
            @ApiResponse(responseCode = "400", description = "if body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> saveNewAppointment(@RequestBody AppointmentPostRequestBody appointmentsPostRequestBody) {

        if(new Date().after(ConvertLocalDateToDateType.convertFrom(appointmentsPostRequestBody.getAppointmentDate()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The invalid date of birth");
        }

        Optional<Doctor> doctorExist = doctorServices.findByCrm(appointmentsPostRequestBody.getCrmDoctor());
        Optional<Patients> patientExist = patientServices.findByCPF(appointmentsPostRequestBody.getCpfPatient());


        if (doctorExist.isPresent() && patientExist.isPresent()) {
            Optional<Appointments> appointmentsExists = appointmentsServices.findByDoctorAndPatient(doctorExist.get(), patientExist.get());

            if (appointmentsExists.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a pending appointment for this patient");
            }
        }


        if (doctorExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor not found");
        }

        if (patientExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient not found");
        }


        appointmentsServices.save(appointmentsPostRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body("Appointmet created sucessefully");
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit appointments", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success   edit appointment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentPutRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "If appointment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "400", description = "if body is incorrect")
    })
    public ResponseEntity<Object> editAppointment(@RequestBody AppointmentPutRequestBody appointmentsPutRequestBody) {

        if(new Date().after(ConvertLocalDateToDateType.convertFrom(appointmentsPutRequestBody.getAppointmentDate()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The invalid date of birth");
        }

        Optional<Doctor> doctorExist = doctorServices.findByCrm(appointmentsPutRequestBody.getCrmDoctor());

        Optional<Patients> patientExist = patientServices.findByCPF(appointmentsPutRequestBody.getCpfPatient());

        if(doctorExist.isPresent() && patientExist.isPresent()){
            Optional<Appointments> appointmentExist = appointmentsServices.findByDoctorAndPatient(doctorExist.get(), patientExist.get());

            if(appointmentExist.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(appointmentsServices.editAppointmentInfo(appointmentsPutRequestBody));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment not found, check the parameters and try again");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Parameters Error, check and try again later");

    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete appointment", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success delete appointment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDeleteRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "If Appointment not found"),
            @ApiResponse(responseCode = "409", description = "If body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody) {

        Optional<Doctor> doctorExist = doctorServices.findByCrm(appointmentDeleteBody.getCrmDoctor());

        Optional<Patients> patientExist = patientServices.findByCPF(appointmentDeleteBody.getCpfPatient());


        if(patientExist.isPresent() && doctorExist.isPresent()){
            Optional<Appointments> appointmentExist = appointmentsServices.findByDoctorAndPatient(doctorExist.get(), patientExist.get());

            if(appointmentExist.isPresent()){
                appointmentsServices.deleteAppointment(appointmentDeleteBody);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Appointment deleted successfully");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment wasn't found");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check parameters and try again");
    }

    @PutMapping("/attended")
    @Operation(summary = "Switch Patient Statement", description = "To perform the request, it is necessary to have the permission of (HEADNURSE, ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient attended successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDeleteRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "If Appointment not found"),
            @ApiResponse(responseCode = "409", description = "If patient has already been treated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> patientAttended(SwitchPatientStatementRequestBody switchPatientStatementRequestBody) {

        Optional<Doctor> doctorExist = doctorServices.findByCrm(switchPatientStatementRequestBody.getCrmDoctor());

        Optional<Patients> patientExist = patientServices.findByCPF(switchPatientStatementRequestBody.getCpfPatient());

        if(patientExist.isPresent() && doctorExist.isPresent()){
            Optional<Appointments> appointmentExist = appointmentsServices.findByDoctorAndPatient(doctorExist.get(), patientExist.get());


            if(appointmentExist.isPresent()){
                appointmentsServices.switchPatientStatement(switchPatientStatementRequestBody);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The patient's attended status was successfully changed to true");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment wasn't found");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check parameters and try again");
    }

}
