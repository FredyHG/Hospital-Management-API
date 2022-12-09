package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

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
    @DisplayName("Tests for Appointment repository")
    void save_PersistAppointment_WhenSuccessful(){

        Appointments appointmentToBeSaved = createAppointment();
        appointmentsRepository.save(appointmentToBeSaved);

        Assertions.assertThat(appointmentToBeSaved).isNotNull();
        Assertions.assertThat(appointmentToBeSaved.getId()).isNotNull();
        Assertions.assertThat(appointmentToBeSaved.getPatient()).isEqualTo(patientsRepository.findByCpf("123").get());
        Assertions.assertThat(appointmentToBeSaved.getDoctor()).isEqualTo(doctorRepository.findByCrm("123").get());
    }

    private Appointments createAppointment(){
        Doctor doctor = Doctor.builder()
                .firstName("Fredy")
                .lastName("Gomes")
                .crm("123")
                .build();

        doctorRepository.save(doctor);

        Patients patients = Patients.builder()
                .firstName("Arthur")
                .lastName("Romas")
                .cpf("123")
                .build();

        patientsRepository.save(patients);

        return Appointments.builder()
                .appointmentDate(new Date())
                .patient(patients)
                .doctor(doctor)
                .patientAttended(false)
                .drugAllergy("Pinga")
                .build();

    }

}