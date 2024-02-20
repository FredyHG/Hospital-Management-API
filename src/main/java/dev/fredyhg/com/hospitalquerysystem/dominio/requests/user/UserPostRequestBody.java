package dev.fredyhg.com.hospitalquerysystem.dominio.requests.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequestBody {

    private String username;

    private String password;

}
