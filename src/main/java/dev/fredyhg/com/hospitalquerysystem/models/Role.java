package dev.fredyhg.com.hospitalquerysystem.models;


import dev.fredyhg.com.hospitalquerysystem.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TB_ROLE")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;


    public Role(String roleName) {
        this.roleName = RoleName.valueOf(roleName);
    }

    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }
}
