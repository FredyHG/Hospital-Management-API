package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorDto;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.DoctorServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "List all doctors", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all doctor"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Page<Doctor>> listAllDoctor(Pageable pageable){
        return new ResponseEntity<>(doctorServices.listAllDoctors(pageable), HttpStatus.OK);
    }

    @GetMapping("/find/{crm}")
    @Operation(summary = "Find doctor by CRM", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List doctor"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<DoctorDto> findByCrm(@PathVariable String crm){
        return doctorServices.findByCrm(crm);
    }

    @PostMapping("/save")
    @Operation(summary = "Create new doctor", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Doctor already exists"),
            @ApiResponse(responseCode = "201", description = "Dcotor create successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> saveNewDoctor(@RequestBody @Valid DoctorPostRequestBody doctorPostRequestBody){
        return doctorServices.save(doctorPostRequestBody);
    }

    @PutMapping("/admin/update")
    @Operation(summary = "Update doctor info", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor info edited successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Doctor> replaceDoctor(@RequestBody DoctorPutRequestBody doctorPutRequestBody){
        return doctorServices.replace(doctorPutRequestBody);
    }

    @DeleteMapping("/admin/delete/{crm}")
    @Operation(summary = "Delete doctor by CRM", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteDoctorByCrm(@PathVariable String crm){
        return doctorServices.deleteByCrm(crm);
    }

}
