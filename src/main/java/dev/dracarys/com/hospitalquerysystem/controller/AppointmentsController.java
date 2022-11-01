package dev.dracarys.com.hospitalquerysystem.controller;


import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentsDto;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentsPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentsPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.AppointmentsServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/hospital/appointments")
@Log4j2
@RequiredArgsConstructor
public class AppointmentsController {

    private final AppointmentsServices appointmentsServices;

    @GetMapping("/list")
    public ResponseEntity<List<AppointmentsDto>> findAll(){
        return new ResponseEntity<>(appointmentsServices.listAllAppointments(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> saveNewAppointment(@RequestBody AppointmentsPostRequestBody appointmentsPostRequestBody){
        return appointmentsServices.save(appointmentsPostRequestBody);
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editAppointment(@RequestBody AppointmentsPutRequestBody appointmentsPutRequestBody){
        return appointmentsServices.editAppointmentInfo(appointmentsPutRequestBody);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody){
        return appointmentsServices.deleteAppointment(appointmentDeleteBody);
    }

}
