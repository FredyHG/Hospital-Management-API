package dev.dracarys.com.hospitalquerysystem.repository;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {

}
