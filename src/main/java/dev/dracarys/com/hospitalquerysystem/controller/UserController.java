package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.requests.user.UserAddRoleRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserServices userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserPostRequestBody userPostRequestBody){
        return userService.createNewUser(userPostRequestBody);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody UserDeleteRequestBody userDeleteRequestBody){
        return userService.deleteUser(userDeleteRequestBody);
    }

    @PostMapping("/addrole")
    public ResponseEntity<Object> addRole(@RequestBody UserAddRoleRequestBody userAddRoleRequestBody){
        return userService.addRole(userAddRoleRequestBody);
    }

}
