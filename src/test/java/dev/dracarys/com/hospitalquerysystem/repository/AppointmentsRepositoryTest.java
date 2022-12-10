package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.repository.util.CreateAppointment;
import dev.dracarys.com.hospitalquerysystem.repository.util.DoctorCreate;
import dev.dracarys.com.hospitalquerysystem.repository.util.PatientCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Appointment repository test")
class AppointmentsRepositoryTest {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientsRepository patientsRepository;

    @Test
    @DisplayName("SavePersistAppointmentWhenSuccessful")
    void save_PersistAppointment_WhenSuccessful(){

        Doctor doctor = DoctorCreate.createValidDoctor();
        doctorRepository.save(doctor);

        Patients patients = PatientCreate.createValidPatient();
        patientsRepository.save(patients);

        Appointments appointmentToBeSaved = CreateAppointment.createValidAppointment(doctor, patients);
        appointmentsRepository.save(appointmentToBeSaved);

        Assertions.assertThat(appointmentToBeSaved).isNotNull();
        Assertions.assertThat(appointmentToBeSaved.getId()).isNotNull();
        Assertions.assertThat(appointmentToBeSaved.getPatient()).isEqualTo(patientsRepository.findByCpf("123").get());
        Assertions.assertThat(appointmentToBeSaved.getDoctor()).isEqualTo(doctorRepository.findByCrm("123").get());
    }

    @Test
    @DisplayName("RemoveAppointmentWhenSuccessful")
    void delete_PersistAppointment_WhenSuccessful(){

        Doctor doctor = DoctorCreate.createValidDoctor();
        doctorRepository.save(doctor);

        Patients patients = PatientCreate.createValidPatient();
        patientsRepository.save(patients);

        Appointments appointmentToBeSaved = CreateAppointment.createValidAppointment(doctor, patients);
        appointmentsRepository.save(appointmentToBeSaved);

        Optional<Appointments> appointmentsExist = appointmentsRepository.findByDoctorAndPatient(appointmentToBeSaved.getDoctor(), appointmentToBeSaved.getPatient());

        Assertions.assertThat(appointmentsExist).isNotEmpty();
        Assertions.assertThat(appointmentsExist).get().isEqualTo(appointmentToBeSaved);

        appointmentsRepository.delete(appointmentsExist.get());

        Optional<Appointments> appointmentsDeleted = appointmentsRepository.findByDoctorAndPatient(appointmentToBeSaved.getDoctor(), appointmentToBeSaved.getPatient());

        Assertions.assertThat(appointmentsDeleted).isEmpty();

    }

}