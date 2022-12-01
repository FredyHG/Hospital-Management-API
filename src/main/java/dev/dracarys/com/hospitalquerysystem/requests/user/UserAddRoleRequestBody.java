package dev.dracarys.com.hospitalquerysystem.requests.user;

import dev.dracarys.com.hospitalquerysystem.enums.RoleName;
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
