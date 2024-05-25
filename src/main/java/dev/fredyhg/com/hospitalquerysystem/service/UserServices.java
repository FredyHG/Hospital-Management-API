package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.dominio.Role;
import dev.fredyhg.com.hospitalquerysystem.dominio.UserModel;
import dev.fredyhg.com.hospitalquerysystem.enums.RoleName;
import dev.fredyhg.com.hospitalquerysystem.exception.RoleNotFoundException;
import dev.fredyhg.com.hospitalquerysystem.exception.user.UserAlreadyExist;
import dev.fredyhg.com.hospitalquerysystem.exception.user.UserNotFound;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.RoleRepository;
import dev.fredyhg.com.hospitalquerysystem.repository.UserRepository;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserAddRoleRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(UserPostRequestBody userPostRequestBody) {

        ensureUserExist(userPostRequestBody.getUsername());

        String passwordEncrypt = passwordEncoder.encode(userPostRequestBody.getPassword());
        userPostRequestBody.setPassword(passwordEncrypt);

        UserModel userToBeSaved = ModelMappers.userPostRequestToUserModel(userPostRequestBody);

        userRepository.save(userToBeSaved);

    }

    public void deleteByUsername(String username) {

        Optional<UserModel> userToBeDeleted = userRepository.findByUsername(username);

        userToBeDeleted.ifPresent(userRepository::delete);
    }

    @Transactional
    public void addRole(UserAddRoleRequestBody userAddRoleRequestBody) {

        UserModel user = ensureUserExist(userAddRoleRequestBody.getUsername());
        Role role = ensureRoleExist(userAddRoleRequestBody.getRole());

        user.addRole(role);

        userRepository.save(user);
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserModel ensureUserExist(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound("User not found"));
    }

    public void ensureUserNotExist(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new UserAlreadyExist("Username already exists");
        });
    }

    public Role ensureRoleExist(RoleName role) {
        return roleRepository.findByRoleName(role)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }
}
