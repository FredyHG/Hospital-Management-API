package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorGetReturnObject;
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
import java.util.Optional;

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
        return ResponseEntity.status(HttpStatus.OK).body(doctorServices.listAllDoctors(pageable));
    }

    @GetMapping("/find/{crm}")
    @Operation(summary = "Find doctor by CRM", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List doctor"),
            @ApiResponse(responseCode = "400", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> findByCrm(@PathVariable String crm){

        Optional<DoctorGetReturnObject> doctorExists = doctorServices.findByCrmReturnDTO(crm);

        return doctorExists.<ResponseEntity<Object>>map(doctorGetReturnObject -> ResponseEntity.status(HttpStatus.OK)
                .body(doctorGetReturnObject)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor wasn't found"));

    }

    @PostMapping("/create")
    @Operation(summary = "Create new doctor", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Doctor already exists"),
            @ApiResponse(responseCode = "201", description = "Dcotor create successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> saveNewDoctor(@RequestBody @Valid DoctorPostRequestBody doctorPostRequestBody){

        Optional<DoctorGetReturnObject> doctorExist = doctorServices.findByCrmReturnDTO(doctorPostRequestBody.getCrm());

        if(doctorExist.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("It was not possible to save the doctor, because there is already a doctor with the same crm "
                            + doctorPostRequestBody.getCrm() + " registered");
        }

        doctorServices.save(doctorPostRequestBody);

        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor save successfully");
    }

    @PutMapping("/admin/update")
    @Operation(summary = "Update doctor info", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor info edited successfully"),
            @ApiResponse(responseCode = "400", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> replaceDoctor(@RequestBody DoctorPutRequestBody doctorPutRequestBody){

        Optional<DoctorGetReturnObject> doctorExist = doctorServices.findByCrmReturnDTO(doctorPutRequestBody.getCrm());

        if(doctorExist.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(doctorServices.replace(doctorPutRequestBody));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor wasn't found");
    }

    @DeleteMapping("/admin/delete/{crm}")
    @Operation(summary = "Delete doctor by CRM", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteDoctorByCrm(@PathVariable String crm){

        Optional<DoctorGetReturnObject> doctorExist = doctorServices.findByCrmReturnDTO(crm);

        if(doctorExist.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor wasn't found");
        }
        doctorServices.deleteByCrm(crm);
        return ResponseEntity.status(HttpStatus.OK).body("Doctor deleted successfully");
    }

}
