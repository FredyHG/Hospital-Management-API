package dev.dracarys.com.hospitalquerysystem.repository.employee;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
    Page<Doctor> findByCrm(Long crm, Pageable pageable);

    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crm")
    Optional<Doctor> findByCrmOptional(Long crm);


    @Query(value = "SELECT p FROM Doctor p WHERE p.crm = :crmNonPageable")
    Optional<Doctor> findByCrmNonPageable(Long crmNonPageable);

    @Query(value = "SELECT p FROM Doctor p WHERE p.firstName = :firstName")
    Page<Doctor> findByFirstName(String firstName, Pageable pageable);

    @Query(value = "SELECT p FROM Doctor p WHERE p.firstName = :firstName")
    Optional<Doctor> findByFirstNameOptional(String firstName);

    Long deleteByCrm(Long crm);

}
