package dev.dracarys.com.hospitalquerysystem.repository.employee;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.HeadNurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadNurseRepository extends JpaRepository<HeadNurse, Long> {
}
