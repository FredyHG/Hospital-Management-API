package dev.fredyhg.com.hospitalquerysystem.controller.impl;

import dev.fredyhg.com.hospitalquerysystem.dominio.Doctor;
import dev.fredyhg.com.hospitalquerysystem.dominio.Patient;
import dev.fredyhg.com.hospitalquerysystem.dominio.Stay;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayDeleteRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.service.DoctorServices;
import dev.fredyhg.com.hospitalquerysystem.service.PatientServices;
import dev.fredyhg.com.hospitalquerysystem.service.StayServices;
import dev.fredyhg.com.hospitalquerysystem.utils.ConvertLocalDateToDateType;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/hospital/stay")
@Log4j2
@RequiredArgsConstructor
public class StayController {

    private final StayServices stayServices;

    @PostMapping("/create")
    @Operation(summary = "Create new stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success created new stay", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StayPostRequestBody.class))}),
            @ApiResponse(responseCode = "400", description = "Already exists stay"),
            @ApiResponse(responseCode = "400", description = "doctor or patient not exist"),
            @ApiResponse(responseCode = "409", description = "body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> createNewStay(@RequestBody StayPostRequestBody stayPostRequestBody) {
        stayServices.createNewStay(stayPostRequestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stay created successfully");
    }


    @GetMapping("/list")
    @Operation(summary = "List all stays", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT / DOCTOR)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success list all stays"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<List<StayGetReturnObject>> findAll() {
        return new ResponseEntity<>(stayServices.listAllStays(), HttpStatus.OK);
    }


    @PutMapping("/edit")
    @Operation(summary = "Edit stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "400", description = "Stay not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "409", description = "Body is incorrect")
    })
    public ResponseEntity<ResponseMessage> editStay(@RequestBody StayPutRequestBody stayPutRequestBody) {

        stayServices.editStayInfo(stayPutRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Stay details updated successfully"));
    }


    @DeleteMapping("/delete")
    @Operation(summary = "Delete stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "400", description = "Stay not found"),
            @ApiResponse(responseCode = "409", description = "Body is incorrect")
    })
    public ResponseEntity<ResponseMessage> deleteStay(StayDeleteRequestBody stayDeleteRequestBody) {

        stayServices.deleteStay(stayDeleteRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Stay delete successfully"));
    }
}
