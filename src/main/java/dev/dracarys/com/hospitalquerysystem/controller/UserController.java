package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.requests.user.UserAddRoleRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.user.UserPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create new user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Data conflict"),
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> createUser(@RequestBody UserPostRequestBody userPostRequestBody){
        return userService.createNewUser(userPostRequestBody);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "201", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> deleteUser(@RequestBody UserDeleteRequestBody userDeleteRequestBody){
        return userService.deleteUser(userDeleteRequestBody);
    }

    @PostMapping("/addrole")
    @Operation(summary = "Add role in user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "User data conflict"),
            @ApiResponse(responseCode = "201", description = "Role added successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    public ResponseEntity<Object> addRole(@RequestBody UserAddRoleRequestBody userAddRoleRequestBody){
        return userService.addRole(userAddRoleRequestBody);
    }

}
