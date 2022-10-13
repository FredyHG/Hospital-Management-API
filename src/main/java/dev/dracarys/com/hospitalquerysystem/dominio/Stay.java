package dev.dracarys.com.hospitalquerysystem.dominio;

import dev.dracarys.com.hospitalquerysystem.enums.StatusStay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date stayDate;
    @NotEmpty(message = "The field 'doctorId' cannot be empty")
    private Long doctorId;
    private String drugAllergy;
    private String description;
    @NotEmpty(message = "The field 'status' cannot be empty")
    private StatusStay status;
    @NotEmpty(message = "The field 'room' cannot be empty")
    private Long room;
    private Date modifiedIn;
}
