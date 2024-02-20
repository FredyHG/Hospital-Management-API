package dev.fredyhg.com.hospitalquerysystem.repository;

import dev.fredyhg.com.hospitalquerysystem.dominio.Appointment;
import dev.fredyhg.com.hospitalquerysystem.dominio.Doctor;
import dev.fredyhg.com.hospitalquerysystem.dominio.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT p FROM Appointment p WHERE p.doctor = :doctor")
    List<Appointment> findByDoctor(@Param("doctor") Doctor doctor);

    @Query(value = "SELECT p FROM Appointment p WHERE p.doctor = :doctor AND p.patient = :patient AND p.patientAttended = false")
    Optional<Appointment> findByDoctorAndPatient(@Param("doctor") Doctor doctor, @Param("patient") Patient patient);

}
