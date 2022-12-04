package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.mapper.PatientMapper;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.util.ConvertLocalDateToDateType;
import dev.dracarys.com.hospitalquerysystem.util.TitleCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServices {

    private final PatientsRepository patientsRepository;

    @Transactional
    public void save(PatientPostRequestBody patients){

        Optional<Patients> patientToBeSaved = Optional.of(PatientMapper.INSTANCE.toPatient(patients));

        patientToBeSaved.get().setFirstName(TitleCase.convertToTitleCaseIteratingChars(patientToBeSaved.get().getFirstName()));
        patientToBeSaved.get().setLastName(TitleCase.convertToTitleCaseIteratingChars(patientToBeSaved.get().getLastName()));
        patientToBeSaved.get().setBirthdate(ConvertLocalDateToDateType.convertFrom(patients.getBirthdate()));

        patientsRepository.save(patientToBeSaved.get());
    }


    public Optional<Patients> findByCPF(String cpf){
        return patientsRepository.findByCpf(cpf);
    }

    public List<Patients> findByName(String name) {

        return patientsRepository.findByName(name);

    }

    public Page<Patients> listAllPatients(Pageable pageable) {
        return patientsRepository.findAll(pageable);
    }

    public List<Patients> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientsRepository.findPatientByfirstNameAndLastName(firstName, lastName);
    }

    public void deleteByCpf(String cpf) {

        Optional<Patients> patientToBeDelete = patientsRepository.findByCpf(cpf);
        patientToBeDelete.ifPresent(patientsRepository::delete);

    }
}
