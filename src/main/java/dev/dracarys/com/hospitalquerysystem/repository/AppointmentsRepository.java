package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {

    @Query(value = "SELECT p FROM Appointments p WHERE p.doctor = :doctor")
    List<Appointments> findByDoctorId(@Param("doctor") Doctor doctor);

    @Query(value = "SELECT p FROM Appointments p WHERE p.doctor = :doctor AND p.patient = :patient AND p.patientAttended = false")
    Optional<Appointments> findByDoctorAndPatient(@Param("doctor") Doctor doctor, @Param("patient")Patients patients);

//    @Query(value = "SELECT p FROM Appointments p WHERE p.doctor = :doctor AND p.patient = :patient AND p.patientAttended = true")
//    Optional<Appointments> verifyAppointmentsExists(@Param("doctor") Doctor doctor, @Param("patient")Patients patients);
}
