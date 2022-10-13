package dev.dracarys.com.hospitalquerysystem.controller.employee;


import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.employees.DoctorServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/hospital/doctor")
@Log4j2
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorServices doctorServices;




    @PostMapping("/save")
    public ResponseEntity<Object> saveNewDoctor(@RequestBody @Valid DoctorPostRequestBody doctorPostRequestBody){
        return doctorServices.save(doctorPostRequestBody);
    }

}
