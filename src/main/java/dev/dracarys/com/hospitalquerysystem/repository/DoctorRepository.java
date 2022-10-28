package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
    Optional<Doctor> findByCrm(String crm);
//    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
//    Doctor findByCrm(Long crm);

    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
    Optional<Doctor> findByCrmOptional(String crm);


    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crmNonPageable")
    Optional<Doctor> findByCrmNonPageable(String crmNonPageable);

    @Query(value = "SELECT p FROM Doctor p WHERE p.firstName = :firstName")
    List<Doctor> findByFirstName(String firstName);

    @Query(value = "SELECT p FROM Doctor p WHERE p.firstName = :firstName")
    Optional<Doctor> findByFirstNameOptional(String firstName);


    Long deleteByCrm(String crm);

}
