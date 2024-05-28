package dev.fredyhg.com.hospitalquerysystem.models;


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
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthdate;

    private String phone;


    @JsonManagedReference
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @JsonManagedReference
    @OneToMany(mappedBy = "patient")
    private List<Stay> stays;
}
