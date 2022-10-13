package dev.dracarys.com.hospitalquerysystem.dominio.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor")
public class HeadNurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 3,max = 16)
    private String firstName;
    @Length(min = 3,max = 16)
    private String lastName;
}
