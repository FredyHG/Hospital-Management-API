package dev.fredyhg.com.hospitalquerysystem.repository;

import dev.fredyhg.com.hospitalquerysystem.dominio.Doctor;
import dev.fredyhg.com.hospitalquerysystem.dominio.Patient;
import dev.fredyhg.com.hospitalquerysystem.dominio.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

    @Query(value = "SELECT p FROM Stay p WHERE p.doctor = :doctor AND p.patient = :patient AND p.status = 'OBSERVATION' OR p.status = 'ADMITTED'")
    Optional<Stay> findByDoctorAndPatient(@Param("doctor") Doctor doctor, @Param("patient") Patient patient);

    @Query(value = "SELECT p FROM Stay p WHERE p.doctor = :doctor")
    List<Stay> findByDoctor(@Param("doctor") Doctor doctor);
}
