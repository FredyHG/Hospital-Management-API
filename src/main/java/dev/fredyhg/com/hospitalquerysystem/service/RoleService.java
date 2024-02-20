package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.dominio.Role;
import dev.fredyhg.com.hospitalquerysystem.enums.RoleName;
import dev.fredyhg.com.hospitalquerysystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findRoleByName(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }



}
