package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.dominio.Role;
import dev.dracarys.com.hospitalquerysystem.dominio.UserModel;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserAddRoleRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.RoleService;
import dev.dracarys.com.hospitalquerysystem.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserServices userService;

    private final RoleService roleService;

    @PostMapping("/create")
    @Operation(summary = "Create new user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Data conflict"),
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> createUser(@RequestBody UserPostRequestBody userPostRequestBody){

        String regularExpression = "^(?=.{4,20}$)(?:[a-zA-Z\\d]+(?:[._][a-zA-Z\\d])*)+$";

        if(!userPostRequestBody.getPassword().matches(regularExpression)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password contains invalid arguments");
        }

        if(!userPostRequestBody.getUsername().matches(regularExpression)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username contains invalid arguments");
        }

        if(userService.findByUsername(userPostRequestBody.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        userService.createNewUser(userPostRequestBody);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created sucessfully");
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "201", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteUser(@RequestBody UserDeleteRequestBody userDeleteRequestBody) {

        Optional<UserModel> userExist = userService.findByUsername(userDeleteRequestBody.getUserName());

        if (userExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username not found");
        }

        userService.deleteUser(userDeleteRequestBody);

        return ResponseEntity.status(HttpStatus.OK).body("User delete successfully");
    }

    @PostMapping("/addrole")
    @Operation(summary = "Add role in user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "User data conflict"),
            @ApiResponse(responseCode = "201", description = "Role added successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> addRole(@RequestBody UserAddRoleRequestBody userAddRoleRequestBody){

        Optional<Role> roleToBeAdd = roleService.findRoleByName(userAddRoleRequestBody.getRole());
        Optional<UserModel> userExist = userService.findByUsername(userAddRoleRequestBody.getUsername());

        if(roleToBeAdd.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Role not found");
        }

        if(userExist.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User not found");
        }

        userService.addRole(userAddRoleRequestBody);
        return ResponseEntity.status(HttpStatus.OK).body("Role added successfully");
    }

}
