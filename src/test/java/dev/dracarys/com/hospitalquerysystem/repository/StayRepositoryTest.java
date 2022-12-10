package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import dev.dracarys.com.hospitalquerysystem.repository.util.CreateStay;
import dev.dracarys.com.hospitalquerysystem.repository.util.DoctorCreate;
import dev.dracarys.com.hospitalquerysystem.repository.util.PatientCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Stay repository test")
class StayRepositoryTest {


    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientsRepository patientsRepository;

    @Test
    @DisplayName("SavePersistStayWhenSuccessful")
    void save_PersistAppointment_WhenSuccessful(){

        Doctor doctor = DoctorCreate.createValidDoctor();
        doctorRepository.save(doctor);

        Patients patients = PatientCreate.createValidPatient();
        patientsRepository.save(patients);

        Stay stayToBeSaved = CreateStay.createValidStay(doctor, patients);
        stayRepository.save(stayToBeSaved);

        Assertions.assertThat(stayToBeSaved).isNotNull();
        Assertions.assertThat(stayToBeSaved.getId()).isNotNull();
        Assertions.assertThat(stayToBeSaved.getPatient()).isEqualTo(patientsRepository.findByCpf("123").get());
        Assertions.assertThat(stayToBeSaved.getDoctor()).isEqualTo(doctorRepository.findByCrm("123").get());
    }

    @Test
    @DisplayName("RemoveStayWhenSuccessful")
    void delete_PersistAppointment_WhenSuccessful(){

        Doctor doctor = DoctorCreate.createValidDoctor();
        doctorRepository.save(doctor);

        Patients patients = PatientCreate.createValidPatient();
        patientsRepository.save(patients);

        Stay stayToBeSaved = CreateStay.createValidStay(doctor, patients);
        stayRepository.save(stayToBeSaved);

        Optional<Stay> appointmentsExist = stayRepository.findByDoctorAndPatient(stayToBeSaved.getDoctor(), stayToBeSaved.getPatient());

        Assertions.assertThat(appointmentsExist).isNotEmpty();
        Assertions.assertThat(appointmentsExist).get().isEqualTo(stayToBeSaved);

        stayRepository.delete(appointmentsExist.get());

        Optional<Stay> stayDeleted = stayRepository.findByDoctorAndPatient(stayToBeSaved.getDoctor(), stayToBeSaved.getPatient());

        Assertions.assertThat(stayDeleted).isEmpty();

    }

}