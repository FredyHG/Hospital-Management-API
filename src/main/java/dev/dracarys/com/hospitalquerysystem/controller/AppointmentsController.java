package dev.dracarys.com.hospitalquerysystem.controller;


import dev.dracarys.com.hospitalquerysystem.requests.appointments.*;
import dev.dracarys.com.hospitalquerysystem.service.AppointmentsServices;
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

import java.util.List;

@RestController
@RequestMapping("/v1/hospital/appointments")
@Log4j2
@RequiredArgsConstructor
public class AppointmentsController {

    private final AppointmentsServices appointmentsServices;

    @GetMapping("/list")
    @Operation(summary = "List all appointments", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / DOCTOR / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success list all appointments"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<List<AppointmentsDto>> findAll(){
        return new ResponseEntity<>(appointmentsServices.listAllAppointments(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Create new appointment", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success created new appointment", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentsPostRequestBody.class))}),
            @ApiResponse(responseCode = "409", description = "Already exists appointment"),
            @ApiResponse(responseCode = "404", description = "if doctor or patient not exist"),
            @ApiResponse(responseCode = "400", description = "if body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> saveNewAppointment(@RequestBody AppointmentsPostRequestBody appointmentsPostRequestBody){
        return appointmentsServices.save(appointmentsPostRequestBody);
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit appointments", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success   edit appointment", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentsPutRequestBody.class))}),
            @ApiResponse(responseCode = "409", description = "If appointment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> editAppointment(@RequestBody AppointmentsPutRequestBody appointmentsPutRequestBody){
        return appointmentsServices.editAppointmentInfo(appointmentsPutRequestBody);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete appointment", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success delete appointment", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDeleteRequestBody.class))}),
            @ApiResponse(responseCode = "404", description = "If Appointment not found"),
            @ApiResponse(responseCode = "409", description = "If body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody){
        return appointmentsServices.deleteAppointment(appointmentDeleteBody);
    }

    @PutMapping("/attended")
    @Operation(summary = "Switch Patient Statement", description = "To perform the request, it is necessary to have the permission of (HEADNURSE, ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient attended successfully", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDeleteRequestBody.class))}),
            @ApiResponse(responseCode = "404", description = "If Appointment not found"),
            @ApiResponse(responseCode = "409", description = "If patient has already been treated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> patientAttended (SwitchPatientStatementRequestBody switchPatientStatementRequestBody){
        return appointmentsServices.switchPatientStatement(switchPatientStatementRequestBody);
    }

}
