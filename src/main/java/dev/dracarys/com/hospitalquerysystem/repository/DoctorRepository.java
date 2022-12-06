package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
    Optional<Doctor> findByCrm(String crm);

    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
    Optional<Doctor> findByCrmOptional(String crm);



    Long deleteByCrm(String crm);

}
