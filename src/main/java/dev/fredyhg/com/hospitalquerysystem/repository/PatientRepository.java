package dev.fredyhg.com.hospitalquerysystem.repository;

import dev.fredyhg.com.hospitalquerysystem.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT p FROM Patient p WHERE p.firstName = :firstName")
    List<Patient> findByName(@Param("firstName") String name);

    @Query(value = "SELECT p FROM Patient p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    List<Patient> findPatientByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT p FROM Patient p WHERE p.cpf = :cpf")
    Optional<Patient> findByCpf(@Param("cpf") String cpf);

}
