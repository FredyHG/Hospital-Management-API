package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayGetReturnObject;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.DoctorServices;
import dev.dracarys.com.hospitalquerysystem.service.PatientServices;
import dev.dracarys.com.hospitalquerysystem.service.StayServices;
import dev.dracarys.com.hospitalquerysystem.util.ConvertLocalDateToDateType;
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

    private final DoctorServices doctorServices;

    private final PatientServices patientServices;

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

        if(new Date().after(ConvertLocalDateToDateType.convertFrom(stayPostRequestBody.getStayDate()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The invalid date of birth");
        }

        Optional<Doctor> doctorExist = doctorServices.findByCrm(stayPostRequestBody.getCrmDoctor());
        Optional<Patients> patientExist = patientServices.findByCPF(stayPostRequestBody.getCpfPatient());

        if (doctorExist.isPresent() && patientExist.isPresent()) {
            Optional<Stay> stay = stayServices.findStayByDoctorAndPatient(doctorExist.get(), patientExist.get());

            if (stay.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stay already exist");
            }
            stayServices.createNewStay(stayPostRequestBody);
            return ResponseEntity.status(HttpStatus.CREATED).body("Stay created successfully");
        }


        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error check the parameters and try again later");

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
    public ResponseEntity<Object> editStay(@RequestBody StayPutRequestBody stayPutRequestBody) {

        if(new Date().after(ConvertLocalDateToDateType.convertFrom(stayPutRequestBody.getStayDate()))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The invalid date of birth");
        }


        Optional<Doctor> doctorExist = doctorServices.findByCrm(stayPutRequestBody.getCrmDoctor());

        Optional<Patients> patientExist = patientServices.findByCPF(stayPutRequestBody.getCpfPatient());

        if (doctorExist.isPresent() && patientExist.isPresent()) {
            Optional<Stay> stayExist = stayServices.findStayByDoctorAndPatient(doctorExist.get(), patientExist.get());

            if (stayExist.isPresent()) {

                stayServices.editStayInfo(stayPutRequestBody);
                return ResponseEntity.status(HttpStatus.OK).body("Stay details updated successfully");

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stay wasn't found");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check parameters and try again");
    }


    @DeleteMapping("/delete")
    @Operation(summary = "Delete stay", description = "To perform the request, it is necessary to have the permission of (HEADNURSE)", tags = {"STAYS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stay edited successfully"),
            @ApiResponse(responseCode = "400", description = "Stay not found"),
            @ApiResponse(responseCode = "409", description = "Body is incorrect")
    })
    public ResponseEntity<Object> deleteStay(StayDeleteRequestBody stayDeleteRequestBody) {

        Optional<Doctor> doctorExist = doctorServices.findByCrm(stayDeleteRequestBody.getCrmDoctor());

        Optional<Patients> patientExist = patientServices.findByCPF(stayDeleteRequestBody.getCpfPatient());

        if (doctorExist.isPresent() && patientExist.isPresent()) {
            Optional<Stay> stayExist = stayServices.findStayByDoctorAndPatient(doctorExist.get(), patientExist.get());

            if (stayExist.isPresent()) {

                stayServices.deleteStay(stayDeleteRequestBody);
                return ResponseEntity.status(HttpStatus.OK).body("Stay delete successfully");

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stay wasn't found");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check parameters and try again");
    }
}
