package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.dominio.Patient;
import dev.fredyhg.com.hospitalquerysystem.exception.patient.PatientInvalidBirthDate;
import dev.fredyhg.com.hospitalquerysystem.exception.patient.PatientNotFoundException;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.PatientRepository;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.patient.PatientPostRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServices {

    private final PatientRepository patientRepository;

    @Transactional
    public void create(PatientPostRequestBody patientPostRequestBody){

        ensurePatientNotExist(patientPostRequestBody.getCpf());
        ensureValidPatientBirthDate(patientPostRequestBody.getBirthdate());

        Patient patientToBeSaved = ModelMappers.patientPostRequestToPatientModel(patientPostRequestBody);

        log.info("Create patient: {}", patientToBeSaved.getFirstName());
        patientRepository.save(patientToBeSaved);
    }


    public Optional<Patient> findByCPF(String cpf){
        return patientRepository.findByCpf(cpf);
    }

    public List<Patient> findByName(String name) {

        return patientRepository.findByName(name);

    }

    public Page<Patient> listAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public List<Patient> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientRepository.findPatientByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    public void deleteByCpf(String cpf) {
        Patient patientToBeDeleted = ensurePatientExists(cpf);

        log.info("Deleting patient with CPF: {}", patientToBeDeleted.getCpf());
        patientRepository.delete(patientToBeDeleted);
    }

    public Patient ensurePatientExists(String cpfPatient) {
        return patientRepository.findByCpf(cpfPatient).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }

    public void ensurePatientNotExist(String cpf) {
        patientRepository.findByCpf(cpf).ifPresent(patient -> {
            throw new PatientNotFoundException("Patient already exist");
        });

    }

    public void ensureValidPatientBirthDate(LocalDate birthDate) {
        if(LocalDate.now().isBefore(birthDate)) {
            throw new PatientInvalidBirthDate("Patient invalid birthdate");
        }
    }
}
