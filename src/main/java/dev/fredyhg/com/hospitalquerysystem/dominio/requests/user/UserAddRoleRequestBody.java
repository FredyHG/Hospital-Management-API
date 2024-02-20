package dev.fredyhg.com.hospitalquerysystem.dominio.requests.user;

import dev.fredyhg.com.hospitalquerysystem.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRoleRequestBody {

    private String username;

    private RoleName role;

}
