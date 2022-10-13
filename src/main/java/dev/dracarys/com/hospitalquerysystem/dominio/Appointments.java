package dev.dracarys.com.hospitalquerysystem.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date appointmentDate;
    private Long patientId;
    private Long doctorId;
    private Boolean patientAttended;
    private String drugAllergy;

}
