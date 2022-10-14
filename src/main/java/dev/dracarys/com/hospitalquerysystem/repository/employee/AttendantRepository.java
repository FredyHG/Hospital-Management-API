package dev.dracarys.com.hospitalquerysystem.repository.employee;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Attendant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendantRepository extends JpaRepository<Attendant, Long> {


    @Query(value = "SELECT p FROM Attendant p WHERE p.firstName = :firstName")
    Page<Attendant> findByFirstName(String firstName, Pageable pageable);

    @Query(value = "SELECT p FROM Attendant p WHERE p.firstName = :firstName")
    Optional<Attendant> findByFirstNameOptional(String firstName);

    @Query(value = "SELECT p FROM Attendant p WHERE p.attendantId = :id")
    Optional<Attendant> findByIdAttendant(Long id);

    @Query(value = "SELECT p FROM Attendant p WHERE p.attendantId = :id")
    Page<Attendant> findByIdAttendantPageable(Long id, Pageable pageable);

    Long deleteByAttendantId(Long attendantId);
}
