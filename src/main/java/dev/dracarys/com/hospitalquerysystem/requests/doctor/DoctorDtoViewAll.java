package dev.dracarys.com.hospitalquerysystem.requests.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
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

    @JsonIgnore
    private List<Appointments> appointments;

    @JsonIgnore
    private List<Stay> stays;




}
