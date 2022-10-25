package dev.dracarys.com.hospitalquerysystem.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.dracarys.com.hospitalquerysystem.enums.StatusStay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date stayDate;
    private String drugAllergy;
    private String description;
    private StatusStay status;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "doctors_id")
    private Doctor doctor;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "patient_id")
    private Patients patient;
}
