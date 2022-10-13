package dev.dracarys.com.hospitalquerysystem.dominio;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthdate;
    private String phone;
    private String susCard;
}
