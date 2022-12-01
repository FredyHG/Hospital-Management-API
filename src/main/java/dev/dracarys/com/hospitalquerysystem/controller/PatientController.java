package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.PatientServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        return patientService.findByName(name);
    }

    @GetMapping("/list")
    @Operation(summary = "List all patient pageable", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient pageable list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> listAllPatients(Pageable pageable){
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
            @ApiResponse(responseCode = "409", description = "Patient already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments in request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> save(@RequestBody @Valid PatientPostRequestBody patient){
        return patientService.save(patient);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete patient by id for ADMIN", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteById(@PathVariable Long id){
        return patientService.deleteById(id);
    }

    @DeleteMapping("/delete/cpf/{cpf}")
    @Operation(summary = "Delete Patient by cpf", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteByCpf(@PathVariable String cpf){
        return patientService.deleteByCpf(cpf);
    }


}
