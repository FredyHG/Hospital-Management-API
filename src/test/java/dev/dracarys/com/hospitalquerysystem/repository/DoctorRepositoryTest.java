package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.util.DoctorCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Doctor repository test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;






    @Test
    @DisplayName("SavePersistDoctorWhenSuccessful")
    void save_PersistDoctor_WhenSuccessful(){

        Doctor doctorToBeSaved = DoctorCreate.createValidDoctor();
        doctorRepository.save(doctorToBeSaved);

        Assertions.assertThat(doctorToBeSaved).isNotNull();
        Assertions.assertThat(doctorToBeSaved.getId()).isNotNull();
        Assertions.assertThat(doctorToBeSaved.getCrm()).isEqualTo("123");
        Assertions.assertThat(doctorToBeSaved.getStay()).isEmpty();
        Assertions.assertThat(doctorToBeSaved.getAppointments()).isEmpty();

    }

    @Test
    @DisplayName("RemoveDoctorWhenSuccessful")
    void delete_PersistDoctor_WhenSuccessful(){
        Doctor doctorToBeSaved = DoctorCreate.createValidDoctor();
        doctorRepository.save(doctorToBeSaved);

        Assertions.assertThat(doctorToBeSaved).isNotNull();
        Assertions.assertThat(doctorToBeSaved.getId()).isNotNull();
        Assertions.assertThat(doctorToBeSaved.getCrm()).isEqualTo("123");
        Assertions.assertThat(doctorToBeSaved.getStay()).isEmpty();
        Assertions.assertThat(doctorToBeSaved.getAppointments()).isEmpty();

        Optional<Doctor> doctorToBeDelete = doctorRepository.findByCrm("123");
        doctorRepository.delete(doctorToBeDelete.get());

        Assertions.assertThat(doctorRepository.findByCrm("123")).isEmpty();

    }






}