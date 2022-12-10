package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.repository.util.PatientCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Patient repository test")
class PatientsRepositoryTest {

    @Autowired
    private PatientsRepository patientsRepository;


    @Test
    @DisplayName("SavePersistPatientWhenSuccessful")
    void save_PersistDoctor_WhenSuccessful(){

        Patients patientToBeSaved = PatientCreate.createValidPatient();
        patientsRepository.save(patientToBeSaved);

        Assertions.assertThat(patientToBeSaved).isNotNull();
        Assertions.assertThat(patientToBeSaved.getId()).isNotNull();
        Assertions.assertThat(patientToBeSaved.getCpf()).isEqualTo("123");
        Assertions.assertThat(patientToBeSaved.getStays()).isEmpty();
        Assertions.assertThat(patientToBeSaved.getAppointments()).isEmpty();

    }

    @Test
    @DisplayName("RemovePatientWhenSuccessful")
    void delete_PersistDoctor_WhenSuccessful(){
        Patients patientToBeSaved = PatientCreate.createValidPatient();
        patientsRepository.save(patientToBeSaved);

        Assertions.assertThat(patientToBeSaved).isNotNull();
        Assertions.assertThat(patientToBeSaved.getId()).isNotNull();
        Assertions.assertThat(patientToBeSaved.getCpf()).isEqualTo("123");
        Assertions.assertThat(patientToBeSaved.getStays()).isEmpty();
        Assertions.assertThat(patientToBeSaved.getAppointments()).isEmpty();

        Optional<Patients> patientToBeDelete = patientsRepository.findByCpf("123");
        patientsRepository.delete(patientToBeDelete.get());

        Assertions.assertThat(patientsRepository.findByCpf("123")).isEmpty();

    }
}