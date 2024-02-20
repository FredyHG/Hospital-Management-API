package dev.fredyhg.com.hospitalquerysystem.controller;

import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorDtoViewAll;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DoctorController {

    @Operation(summary = "List all doctors", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all doctor"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<Page<DoctorDtoViewAll>> listAllDoctor(Pageable pageable);

    @Operation(summary = "Find doctor by CRM", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List doctor"),
            @ApiResponse(responseCode = "400", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<DoctorGetReturnObject> findByCrm(String crm);

    @Operation(summary = "Create new doctor", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Doctor already exists"),
            @ApiResponse(responseCode = "201", description = "Doctor create successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> saveNewDoctor(DoctorPostRequestBody doctorPostRequestBody);

    @Operation(summary = "Update doctor info", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor info edited successfully"),
            @ApiResponse(responseCode = "400", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> replaceDoctor(DoctorPutRequestBody doctorPutRequestBody);

    @Operation(summary = "Delete doctor by CRM", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"DOCTORS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Doctor not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<Object> deleteDoctorByCrm(String crm);
}
