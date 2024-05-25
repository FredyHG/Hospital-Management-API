package dev.fredyhg.com.hospitalquerysystem.controller;

import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserAddRoleRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.utils.models.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface UserController {

    @Operation(summary = "Create new user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Data conflict"),
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> createUser(UserPostRequestBody userPostRequestBody);

    @Operation(summary = "Delete user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "201", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> deleteUser(String username);

    @Operation(summary = "Add role in user", description = "To perform the request, it is necessary to have the permission of (ADMIN)", tags = {"USERS"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "User data conflict"),
            @ApiResponse(responseCode = "201", description = "Role added successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    ResponseEntity<ResponseMessage> addRole(UserAddRoleRequestBody userAddRoleRequestBody);
}
