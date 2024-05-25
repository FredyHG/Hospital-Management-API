package dev.fredyhg.com.hospitalquerysystem.controller;

import dev.fredyhg.com.hospitalquerysystem.dominio.Patient;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.patient.PatientPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientController {

    @Operation(summary = "Find Patient by name, return list", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<List<Patient>> findByName(String name);

    @Operation(summary = "List all patient pageable", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient pageable list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<Page<Patient>> listAllPatients(Pageable pageable);

    @Operation(summary = "Find Patient by first name and last name, return list", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success return patient list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<List<Patient>> findByFirstNameAndLastName(String firstName, String lastName);

    @Operation(summary = "Create new Patient", description = "To perform the request, it is necessary to have the permission of (ATTENDANT)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Patient already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments in request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> save(PatientPostRequestBody patient);

    @Operation(summary = "Delete Patient by cpf", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"PATIENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Patient not found"),
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> deleteByCpf(String cpf);

}
