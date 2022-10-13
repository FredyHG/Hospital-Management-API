package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
}
