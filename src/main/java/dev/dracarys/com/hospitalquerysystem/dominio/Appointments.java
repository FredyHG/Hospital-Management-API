package dev.dracarys.com.hospitalquerysystem.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date appointmentDate;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "patients_id")
    private Patients patient;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private Boolean patientAttended;
    private String drugAllergy;

}
