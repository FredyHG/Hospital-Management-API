package dev.dracarys.com.hospitalquerysystem.controller.patients;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.employee.patient.PatientsPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/hospital/patients")
@Log4j2
@RequiredArgsConstructor
public class PatientsController {

    private final PatientService patientService;

    @GetMapping("/{name}")
    public List<Patients> findByName(@PathVariable String name) {
        return patientService.findByName(name);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Patients>> listAllPatients(Pageable pageable){
        return ResponseEntity.ok(patientService.listAllPatients(pageable));
    }

    @GetMapping("/{firstName}/{lastName}")
    public List<Patients> findByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return patientService.findByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping("/create/")
    public ResponseEntity<Object> save(@RequestBody @Valid PatientsPostRequestBody patients){
        Optional<Patients> patientToBeSave = patientService.findByCpf(patients.getCpf());

        if(patientToBeSave.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The patient is already registered");
        }
        return new ResponseEntity<>(patientService.save(patients), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> save(@PathVariable Long id){
        Optional<Patients> patientToBeDelete = patientService.findById(id);
        if(patientToBeDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
        patientService.delete(patientToBeDelete.get());
        return ResponseEntity.status(HttpStatus.OK).body("Patient delete successfully");
    }

    @DeleteMapping("/delete/cpf/{cpf}")
    public ResponseEntity<Object> save(@PathVariable String cpf){
        Optional<Patients> patientToBeDelete = patientService.findByCpf(cpf);
        if(patientToBeDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
        patientService.delete(patientToBeDelete.get());
        return ResponseEntity.status(HttpStatus.OK).body("Patient delete successfully");
    }


}
