package dev.fredyhg.com.hospitalquerysystem.controller;

import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayDeleteRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StayController {

    @Operation(summary = "Create new stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success created new stay", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StayPostRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "Already exists stay"),
            @ApiResponse(responseCode = "400", description = "doctor or patient not exist"),
            @ApiResponse(responseCode = "409", description = "body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> createNewStay(StayPostRequestBody stayPostRequestBody);

    @Operation(summary = "List all stays", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT / DOCTOR)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success list all stays"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<List<StayGetReturnObject>> findAll();

    @Operation(summary = "Edit stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "400", description = "Stay not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "409", description = "Body is incorrect")
    })
    ResponseEntity<ResponseMessage> editStay(StayPutRequestBody stayPutRequestBody);

    @Operation(summary = "Delete stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "400", description = "Stay not found"),
            @ApiResponse(responseCode = "409", description = "Body is incorrect")
    })
    ResponseEntity<ResponseMessage> deleteStay(StayDeleteRequestBody stayDeleteRequestBody);
}
