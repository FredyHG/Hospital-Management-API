package dev.dracarys.com.hospitalquerysystem.requests.doctor;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String crm;
    @JsonIgnore
    private List<Appointments> appointments;
    private List<AppointmentsDto> appointmentsView;


}
