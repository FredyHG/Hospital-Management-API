package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.PatientServices;
import dev.dracarys.com.hospitalquerysystem.util.ConvertLocalDateToDateType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/hospital/patients")
@Log4j2
@RequiredArgsConstructor
public class PatientController {

    private final PatientServices patientService;

    @GetMapping("/{name}")
    @Operation(summary = "Find Patient by name, return list", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findByName(name));
    }

    @GetMapping("/list")
    @Operation(summary = "List all patient pageable", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient pageable list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> listAllPatients(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(patientService.listAllPatients(pageable));
    }

    @GetMapping("/{firstName}/{lastName}")
    @Operation(summary = "Find Patient by first name and last name, return list", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public List<Patients> findByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return patientService.findByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping("/create/")
    @Operation(summary = "Create new Patient", description = "To perform the request, it is necessary to have the permission of (ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Patient already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments in request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> save(@RequestBody @Valid PatientPostRequestBody patient){

        if(new Date().before(ConvertLocalDateToDateType.convertFrom(patient.getBirthdate()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The invalid date of birth");
        }

        Optional<Patients> patientToBeConvert = patientService.findByCPF(patient.getCpf());

        if(patientToBeConvert.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The patient is already registered");
        }

        patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully");
    }

    @DeleteMapping("/delete/cpf/{cpf}")
    @Operation(summary = "Delete Patient by cpf", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Patient not found"),
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteByCpf(@PathVariable String cpf){

        Optional<Patients> patientExist = patientService.findByCPF(cpf);
        if(patientExist.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient wasn't found");
        }

        patientService.deleteByCpf(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Patient deleted sucessfully");
    }


}
