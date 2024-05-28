package dev.fredyhg.com.hospitalquerysystem.controller;

import dev.fredyhg.com.hospitalquerysystem.models.requests.appointments.*;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentController {

    @Operation(summary = "List all appointments", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / DOCTOR / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success list all appointments"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<List<AppointmentGetResponse>> findAll();

    @Operation(summary = "Create new appointment", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success created new appointment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentPostRequest.class))}),
            @ApiResponse(responseCode = "409", description = "Already exists appointment"),
            @ApiResponse(responseCode = "400", description = "if doctor or patient not exist"),
            @ApiResponse(responseCode = "400", description = "if body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> createNewAppointment(AppointmentPostRequest appointmentsPostRequestBody);

    @Operation(summary = "Edit appointments", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success   edit appointment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentPutRequest.class))}),
            @ApiResponse(responseCode = "400", description = "If appointment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "400", description = "if body is incorrect")
    })
    ResponseEntity<ResponseMessage> editAppointment(AppointmentPutRequest appointmentPutRequest);

    @Operation(summary = "Delete appointment", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success delete appointment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDeleteRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "If Appointment not found"),
            @ApiResponse(responseCode = "409", description = "If body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteRequestBody);

    @Operation(summary = "Switch Patient Statement", description = "To perform the request, it is necessary to have the permission of (HEADNURSE, ATTENDANT)", tags = {"APPOINTMENTS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient attended successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentDeleteRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "If Appointment not found"),
            @ApiResponse(responseCode = "409", description = "If patient has already been treated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<Object> patientAttended(SwitchPatientStatementRequestBody switchPatientStatementRequestBody);
}
