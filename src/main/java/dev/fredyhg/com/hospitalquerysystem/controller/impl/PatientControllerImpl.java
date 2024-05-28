package dev.fredyhg.com.hospitalquerysystem.controller.impl;

import dev.fredyhg.com.hospitalquerysystem.controller.PatientController;
import dev.fredyhg.com.hospitalquerysystem.models.Patient;
import dev.fredyhg.com.hospitalquerysystem.models.requests.patient.PatientPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.service.PatientServices;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/hospital/patients")
@RequiredArgsConstructor
public class PatientControllerImpl implements PatientController {

    private final PatientServices patientService;

    @GetMapping("/{name}")
    public ResponseEntity<List<Patient>> findByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findByName(name));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Patient>>  listAllPatients(Pageable pageable){
        return ResponseEntity.ok(patientService.listAllPatients(pageable));
    }

    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<List<Patient>> findByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findByFirstNameAndLastName(firstName, lastName));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> save(@RequestBody @Valid PatientPostRequestBody patient){

        patientService.create(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Patient created successfully"));
    }

    @DeleteMapping("/delete/cpf/{cpf}")
    public ResponseEntity<ResponseMessage> deleteByCpf(@PathVariable String cpf){
        patientService.deleteByCpf(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage("Patient deleted successfully"));
    }


}
