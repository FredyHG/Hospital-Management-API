package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.dominio.Patient;
import dev.fredyhg.com.hospitalquerysystem.exception.patient.PatientNotFoundException;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.PatientRepository;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.patient.PatientPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.utils.ConvertLocalDateToDateType;
import dev.fredyhg.com.hospitalquerysystem.utils.TitleCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServices {

    private final PatientRepository patientRepository;

    @Transactional
    public void save(PatientPostRequestBody patientPostRequestBody){

        Patient patientToBeSaved = ModelMappers.patientPostRequestToPatientModel(patientPostRequestBody);

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

    public void deleteByCpf(String cpf) {

        Optional<Patient> patientToBeDelete = patientRepository.findByCpf(cpf);
        patientToBeDelete.ifPresent(patientRepository::delete);

    }

    public Patient ensurePatientExists(String cpfPatient) {
        return patientRepository.findByCpf(cpfPatient).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }
}
