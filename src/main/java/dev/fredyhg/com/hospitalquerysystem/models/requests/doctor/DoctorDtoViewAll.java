package dev.fredyhg.com.hospitalquerysystem.models.requests.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


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
