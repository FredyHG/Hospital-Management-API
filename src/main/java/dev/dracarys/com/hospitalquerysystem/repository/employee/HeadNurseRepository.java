package dev.dracarys.com.hospitalquerysystem.repository.employee;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.HeadNurse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeadNurseRepository extends JpaRepository<HeadNurse, Long> {
    @Query(value = "SELECT p FROM HeadNurse p WHERE p.coren = :coren")
    Optional<HeadNurse> findByCoren(Long coren);

    @Query(value = "SELECT p FROM HeadNurse p WHERE p.firstName = :firstName")
    Page<HeadNurse> findByName(String firstName, Pageable pageable);

    @Query(value = "SELECT p FROM HeadNurse p WHERE p.coren = :coren")
    Page<HeadNurse> findByCorenPage(Long coren, Pageable pageable);
    Long deleteByCoren(Long coren);
}
