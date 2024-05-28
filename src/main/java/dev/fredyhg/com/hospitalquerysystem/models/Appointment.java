package dev.fredyhg.com.hospitalquerysystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date appointmentDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "patients_id")
    private Patient patient;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private Boolean patientAttended;

    private String drugAllergy;

}
