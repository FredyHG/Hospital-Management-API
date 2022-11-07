package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDto;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.StayServices;
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

import java.util.List;

@RestController
@RequestMapping("/v1/hospital/stay")
@Log4j2
@RequiredArgsConstructor
public class StayController {

    private final StayServices stayServices;

    @PostMapping("/create")
    @Operation(summary = "Create new stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success created new stay", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StayPostRequestBody.class))}),
            @ApiResponse(responseCode = "409", description = "Already exists stay"),
            @ApiResponse(responseCode = "404", description = "if doctor or patient not exist"),
            @ApiResponse(responseCode = "400", description = "if body is incorrect"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> createNewStay(@RequestBody StayPostRequestBody stayPostRequestBody){
        return stayServices.createNewStay(stayPostRequestBody);
    }

    @GetMapping("/list")
    @Operation(summary = "List all stays", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT / DOCTOR)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success list all stays"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<List<StayDto>> findAll(){
        return new ResponseEntity<>(stayServices.listAllStays(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE / ATTENDANT)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "404", description = "Stay not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> editStay(@RequestBody StayPutRequestBody stayPutRequestBody){
        return stayServices.editStayInfo(stayPutRequestBody);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "404", description = "Stay not found"),
            @ApiResponse(responseCode = "409", description = "Body is incorrect")
    })
    public ResponseEntity<Object> deleteStay(StayDeleteRequestBody stayDeleteRequestBody){
        return stayServices.deleteStay(stayDeleteRequestBody);
    }
}
