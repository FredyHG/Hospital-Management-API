package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Role;
import dev.dracarys.com.hospitalquerysystem.dominio.UserModel;
import dev.dracarys.com.hospitalquerysystem.mapper.UserMapper;
import dev.dracarys.com.hospitalquerysystem.repository.RoleRepository;
import dev.dracarys.com.hospitalquerysystem.repository.UserRepository;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserAddRoleRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<Object> createNewUser(UserPostRequestBody userPostRequestBody) {

        String regularExpression = "^(?=.{4,20}$)(?:[a-zA-Z\\d]+(?:[._][a-zA-Z\\d])*)+$";

        if(!userPostRequestBody.getPassword().matches(regularExpression)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password contains invalid arguments");
        }

        if(!userPostRequestBody.getUsername().matches(regularExpression)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username contains invalid arguments");
        }

        if(userRepository.findByUsername(userPostRequestBody.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        String passwordEncrypt = new BCryptPasswordEncoder().encode(userPostRequestBody.getPassword());

        userPostRequestBody.setPassword(passwordEncrypt);

        UserModel userToBeSaved = UserMapper.INSTANCE.toUser(userPostRequestBody);

        userRepository.save(userToBeSaved);

        return ResponseEntity.status(HttpStatus.OK).body("Username created successfully");

    }

    public ResponseEntity<Object> deleteUser(UserDeleteRequestBody userDeleteRequestBody) {

        Optional<UserModel> userToBeDeleted = userRepository.findByUsername(userDeleteRequestBody.getUserName());

        if(userToBeDeleted.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        }

        userRepository.delete(userToBeDeleted.get());

        return ResponseEntity.status(HttpStatus.OK).body("User delete successfully");
    }

    public ResponseEntity<Object> addRole(UserAddRoleRequestBody userAddRoleRequestBody) {

        Optional<Role> roleToBeAdd = roleRepository.findByRoleName(userAddRoleRequestBody.getRole());
        Optional<UserModel> userToBeAddRole = userRepository.findByUsername(userAddRoleRequestBody.getUsername());

        if(roleToBeAdd.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Role not found");
        }

        if(userToBeAddRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User not found");
        }

        userToBeAddRole.get().getRoles().add(roleToBeAdd.get());

        userRepository.save(userToBeAddRole.get());

        return ResponseEntity.status(HttpStatus.OK).body("Role added successfully");
    }
}
