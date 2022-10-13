package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Long> {
    @Query(value = "SELECT p FROM Patients p WHERE p.firstName = :firstName")
    List<Patients> findByName(@Param("firstName") String name);

    @Query(value = "SELECT p FROM Patients p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    List<Patients> findPatientByfirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT p FROM Patients p WHERE p.cpf = :cpf")
    Optional<Patients> findByCpf(@Param("cpf") String cpf);
}
