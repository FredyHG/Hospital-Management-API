package dev.fredyhg.com.hospitalquerysystem.controller.impl;

import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayDeleteRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.service.StayServices;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
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
    public ResponseEntity<ResponseMessage> createNewStay(@RequestBody StayPostRequestBody stayPostRequestBody) {

        stayServices.createNewStay(stayPostRequestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Stay created successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<List<StayGetReturnObject>> findAll() {
        return new ResponseEntity<>(stayServices.listAllStays(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseMessage> editStay(@RequestBody StayPutRequestBody stayPutRequestBody) {

        stayServices.editStayInfo(stayPutRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Stay details updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteStay(StayDeleteRequestBody stayDeleteRequestBody) {

        stayServices.deleteStay(stayDeleteRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Stay delete successfully"));
    }
}
