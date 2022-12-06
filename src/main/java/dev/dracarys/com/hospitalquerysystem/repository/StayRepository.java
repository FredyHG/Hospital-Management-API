package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

    @Query(value = "SELECT p FROM Stay p WHERE p.doctor = :doctor AND p.patient = :patient AND p.status = dev.dracarys.com.hospitalquerysystem.enums.StatusStay.OBSERVATION OR p.status = dev.dracarys.com.hospitalquerysystem.enums.StatusStay.ADMITTED")
    Optional<Stay> findByDoctorAndPatient(@Param("doctor") Doctor doctor, @Param("patient") Patients patients);

    @Query(value = "SELECT p FROM Stay p WHERE p.doctor = :doctor")
    List<Stay> findByDoctor(@Param("doctor") Doctor doctor);
}
