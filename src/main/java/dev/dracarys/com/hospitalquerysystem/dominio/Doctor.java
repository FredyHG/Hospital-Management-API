package dev.dracarys.com.hospitalquerysystem.dominio;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String crm;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    private List<Appointments> appointments;

    @JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    private List<Stay> stay;

}
