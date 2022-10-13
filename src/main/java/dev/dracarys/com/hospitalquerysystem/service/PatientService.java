package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.mapper.PatientMapper;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.requests.employee.patient.PatientsPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientsRepository patientsRepository;

    @Transactional
    public Patients save(PatientsPostRequestBody patients){
        return patientsRepository.save(PatientMapper.INSTANCE.toPatient(patients));
    }

    public void delete(Patients patients){
        patientsRepository.delete(patients);
    }
    public List<Patients> findByName(String name) {
        return patientsRepository.findByName(name);
    }

    public Optional<Patients> findById(Long id) {
        return patientsRepository.findById(id);
    }

    public Optional<Patients> findByCpf(String cpf) {
        return patientsRepository.findByCpf(cpf);
    }


    public Page<Patients> listAllPatients(Pageable pageable) {
        return patientsRepository.findAll(pageable);
    }

    public List<Patients> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientsRepository.findPatientByfirstNameAndLastName(firstName, lastName);
    }
}
