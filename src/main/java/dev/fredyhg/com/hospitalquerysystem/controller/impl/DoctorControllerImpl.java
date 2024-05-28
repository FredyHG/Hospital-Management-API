package dev.fredyhg.com.hospitalquerysystem.controller.impl;

import dev.fredyhg.com.hospitalquerysystem.controller.DoctorController;
import dev.fredyhg.com.hospitalquerysystem.models.requests.doctor.DoctorDtoViewAll;
import dev.fredyhg.com.hospitalquerysystem.models.requests.doctor.DoctorGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.models.requests.doctor.DoctorPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.doctor.DoctorPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.service.DoctorServices;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
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
public class DoctorControllerImpl implements DoctorController {

    private final DoctorServices doctorServices;

    @GetMapping("/list")
    public ResponseEntity<Page<DoctorDtoViewAll>> listAllDoctor(@ParameterObject Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(doctorServices.listAllDoctors(pageable));
    }

    @GetMapping("/find/{crm}")
    public ResponseEntity<DoctorGetReturnObject> findByCrm(@PathVariable String crm){

        return ResponseEntity.status(HttpStatus.OK).body(doctorServices.findByCrmReturnDTO(crm));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> saveNewDoctor(@RequestBody @Valid DoctorPostRequestBody doctorPostRequestBody){

        doctorServices.save(doctorPostRequestBody);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Doctor created successfully"));
    }

    @PutMapping("/admin/update")
    public ResponseEntity<ResponseMessage> replaceDoctor(@RequestBody DoctorPutRequestBody doctorPutRequestBody){

        doctorServices.replace(doctorPutRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Doctor edited successfully"));
    }

    @DeleteMapping("/admin/delete/{crm}")
    public ResponseEntity<Object> deleteDoctorByCrm(@PathVariable String crm){

        doctorServices.deleteByCrm(crm);
        return ResponseEntity.status(HttpStatus.OK).body("Doctor deleted successfully");
    }

}
