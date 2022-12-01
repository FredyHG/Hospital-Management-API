package dev.dracarys.com.hospitalquerysystem.requests.doctor;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentGetReturnObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorGetReturnObject {

    @JsonIgnore
    private Long id;

    private String firstName;

    private String lastName;

    private String crm;

    @JsonIgnore
    private List<Appointments> appointments;

    private List<AppointmentGetReturnObject> appointmentsView;


}
