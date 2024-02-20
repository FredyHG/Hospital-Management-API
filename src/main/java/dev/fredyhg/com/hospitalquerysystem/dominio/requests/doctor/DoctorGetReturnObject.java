package dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.fredyhg.com.hospitalquerysystem.dominio.Appointment;
import dev.fredyhg.com.hospitalquerysystem.dominio.Stay;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.appointments.AppointmentGetResponse;
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

    private String firstName;

    private String lastName;

    private String crm;

    private List<AppointmentGetResponse> appointmentsView;

    private List<StayGetReturnObject> staysView;

}
