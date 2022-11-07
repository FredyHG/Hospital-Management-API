package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.mapper.PatientMapper;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientsPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientsRepository patientsRepository;

    @Transactional
    public ResponseEntity<Object> save(PatientsPostRequestBody patients){
        Optional<Patients> patientToBeConvert = patientsRepository.findByCpf(patients.getCpf());
        Optional<Patients> patientToBeSaved = Optional.of(PatientMapper.INSTANCE.toPatient(patients));

        if(patientToBeConvert.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The patient is already registered");
        }
        if(patientToBeSaved.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request Body is incorrect");
        }

        patientsRepository.save(patientToBeSaved.get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully");
    }

    public ResponseEntity<Object> findByName(String name) {

        List<Patients> listPatient = patientsRepository.findByName(name);
        if(listPatient.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No patient found");
        }
        return ResponseEntity.ok(listPatient);
    }


    public Page<Patients> listAllPatients(Pageable pageable) {
        return patientsRepository.findAll(pageable);
    }

    public List<Patients> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientsRepository.findPatientByfirstNameAndLastName(firstName, lastName);
    }

    public ResponseEntity<Object> deleteById(Long id) {
        Optional<Patients> patientToBeDelete = patientsRepository.findById(id);
        if(patientToBeDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
        patientsRepository.delete(patientToBeDelete.get());
        return ResponseEntity.status(HttpStatus.OK).body("Patient delete successfully");
    }

    public ResponseEntity<Object> deleteByCpf(String cpf) {
        Optional<Patients> patientToBeDelete = patientsRepository.findByCpf(cpf);
        if(patientToBeDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
        patientsRepository.delete(patientToBeDelete.get());
        return ResponseEntity.status(HttpStatus.OK).body("Patient delete successfully");
    }
}
