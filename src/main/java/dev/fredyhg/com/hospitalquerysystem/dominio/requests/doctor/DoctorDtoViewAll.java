package dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.fredyhg.com.hospitalquerysystem.dominio.Appointment;
import dev.fredyhg.com.hospitalquerysystem.dominio.Stay;
import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DoctorDtoViewAll {
    @JsonIgnore
    private Long id;

    private String firstName;

    private String lastName;

    private String crm;


}
