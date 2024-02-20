package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.dominio.Role;
import dev.fredyhg.com.hospitalquerysystem.dominio.UserModel;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.RoleRepository;
import dev.fredyhg.com.hospitalquerysystem.repository.UserRepository;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserAddRoleRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserDeleteRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void createNewUser(UserPostRequestBody userPostRequestBody) {

        String passwordEncrypt = new BCryptPasswordEncoder().encode(userPostRequestBody.getPassword());

        userPostRequestBody.setPassword(passwordEncrypt);

        UserModel userToBeSaved = ModelMappers.userPostRequestToUserModel(userPostRequestBody);

        userRepository.save(userToBeSaved);

    }

    public void deleteUser(UserDeleteRequestBody userDeleteRequestBody) {

        Optional<UserModel> userToBeDeleted = userRepository.findByUsername(userDeleteRequestBody.getUserName());

        userToBeDeleted.ifPresent(userRepository::delete);

    }

    public void addRole(UserAddRoleRequestBody userAddRoleRequestBody) {

        Optional<Role> roleToBeAdd = roleRepository.findByRoleName(userAddRoleRequestBody.getRole());
        Optional<UserModel> userToBeAddRole = userRepository.findByUsername(userAddRoleRequestBody.getUsername());

        if (roleToBeAdd.isPresent() && userToBeAddRole.isPresent()) {
            userToBeAddRole.get().getRoles().add(roleToBeAdd.get());

            userRepository.save(userToBeAddRole.get());
        }

    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
