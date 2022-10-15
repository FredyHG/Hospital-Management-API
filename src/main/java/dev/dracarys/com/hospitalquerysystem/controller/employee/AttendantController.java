package dev.dracarys.com.hospitalquerysystem.controller.employee;


import dev.dracarys.com.hospitalquerysystem.dominio.employee.Attendant;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.employees.AttendantServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/hospital/attendant")
@Log4j2
@RequiredArgsConstructor
public class AttendantController {

    private final AttendantServices attendantServices;

    @GetMapping("/list")
    public ResponseEntity<Page<Attendant>> listAllAttendant(Pageable pageable){
        return new ResponseEntity<>(attendantServices.listAllAttendant(pageable), HttpStatus.OK);
    }

    @GetMapping("/find/{firstNameOrId}")
    public ResponseEntity<Page<Attendant>> findByFirstName(@PathVariable String firstNameOrId, Pageable pageable){
        return attendantServices.findByNameOrAttendantId(firstNameOrId, pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveNewAttendant(@RequestBody @Valid AttendantPostRequestBody attendantPostRequestBody){
        return attendantServices.save(attendantPostRequestBody);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<Attendant> replaceAttendant(@RequestBody AttendantPutRequestBody attendantPutRequestBody){
        return attendantServices.replace(attendantPutRequestBody);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Object> deleteAttendant(@PathVariable Long id){
        return new ResponseEntity<>(attendantServices.deleteByAttendantId(id), HttpStatus.OK);
    }

}
