package dev.dracarys.com.hospitalquerysystem.dominio;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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

    @JsonManagedReference
    @OneToMany(mappedBy = "patient")
    private List<Appointments> appointments;

    @JsonManagedReference
    @OneToMany(mappedBy = "patient")
    private List<Stay> stays;
}
