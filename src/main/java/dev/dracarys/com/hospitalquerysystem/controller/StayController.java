package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDto;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.StayServices;
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
    public ResponseEntity<Object> createNewStay(@RequestBody StayPostRequestBody stayPostRequestBody){
        return stayServices.createNewStay(stayPostRequestBody);
    }

    @GetMapping("/list")
    public ResponseEntity<List<StayDto>> findAll(){
        return new ResponseEntity<>(stayServices.listAllStays(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editStay(@RequestBody StayPutRequestBody stayPutRequestBody){
        return stayServices.editStayInfo(stayPutRequestBody);
    }

}
