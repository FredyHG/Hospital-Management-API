package dev.dracarys.com.hospitalquerysystem.controller.employee;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.employees.DoctorServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/hospital/doctor")
@Log4j2
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorServices doctorServices;

    @GetMapping("/list")
    public ResponseEntity<Page<Doctor>> listAllDoctor(Pageable pageable){
        return new ResponseEntity<>(doctorServices.listAllDoctors(pageable), HttpStatus.OK);
    }

    @GetMapping("/find/{crmOrName}")
    public ResponseEntity<Page<Doctor>> findByCrm(@PathVariable String crmOrName, Pageable pageable){
        return doctorServices.findByCrmOrName(crmOrName, pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveNewDoctor(@RequestBody @Valid DoctorPostRequestBody doctorPostRequestBody){
        return doctorServices.save(doctorPostRequestBody);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<Doctor> replaceDoctor(@RequestBody DoctorPutRequestBody doctorPutRequestBody){
        return doctorServices.replace(doctorPutRequestBody);
    }

    @DeleteMapping("/admin/delete/{crm}")
    public ResponseEntity<Object> deleteDoctorByCrm(@PathVariable Long crm){
        return doctorServices.deleteByCrm(crm);
    }

}
