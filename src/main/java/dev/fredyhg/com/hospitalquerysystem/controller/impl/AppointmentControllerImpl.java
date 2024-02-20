package dev.fredyhg.com.hospitalquerysystem.controller.impl;


import dev.fredyhg.com.hospitalquerysystem.controller.AppointmentController;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.appointments.*;
import dev.fredyhg.com.hospitalquerysystem.service.AppointmentServices;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
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
public class AppointmentControllerImpl implements AppointmentController {


    private final AppointmentServices appointmentsServices;

    @GetMapping("/list")
    @Override
    public ResponseEntity<List<AppointmentGetResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentsServices.listAllAppointments()) ;
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseMessage> createNewAppointment(@RequestBody AppointmentPostRequest appointmentsPostRequestBody) {

        appointmentsServices.createNewAppointment(appointmentsPostRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Appointment created successfully"));
    }

    @PutMapping("/edit")
    @Override
    public ResponseEntity<ResponseMessage> editAppointment(@RequestBody AppointmentPutRequest appointmentsPutRequestBody) {

       appointmentsServices.editAppointmentInfo(appointmentsPutRequestBody);

       return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Appointment edited successfully"));
    }

    @DeleteMapping("/delete")
    @Override
    public ResponseEntity<Object> deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody) {

        appointmentsServices.deleteAppointment(appointmentDeleteBody);

        return ResponseEntity.status(HttpStatus.OK).body("Appointment deleted successfully");
    }

    @PutMapping("/attended")
    @Override
    public ResponseEntity<Object> patientAttended(SwitchPatientStatementRequestBody switchPatientStatementRequestBody) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The patient's attended status was successfully changed to true");
    }

}
