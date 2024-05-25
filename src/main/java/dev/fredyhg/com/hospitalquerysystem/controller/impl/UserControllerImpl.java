package dev.fredyhg.com.hospitalquerysystem.controller.impl;

import dev.fredyhg.com.hospitalquerysystem.controller.UserController;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserAddRoleRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.service.RoleService;
import dev.fredyhg.com.hospitalquerysystem.service.UserServices;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Log4j2
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserServices userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createUser(@RequestBody UserPostRequestBody userPostRequestBody){

        userService.create(userPostRequestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("User created successfully"));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String username) {

        userService.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User delete successfully"));
    }

    @PostMapping("/add-role")
    public ResponseEntity<ResponseMessage> addRole(@RequestBody UserAddRoleRequestBody userAddRoleRequestBody){

        userService.addRole(userAddRoleRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User add role successfully"));
    }

}
