package dev.fredyhg.com.hospitalquerysystem.repository;

import dev.fredyhg.com.hospitalquerysystem.models.Role;
import dev.fredyhg.com.hospitalquerysystem.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query(value = "SELECT p FROM Role p WHERE p.roleName = :roleName")
    Optional<Role> findByRoleName(RoleName roleName);

}
