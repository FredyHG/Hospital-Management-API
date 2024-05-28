package dev.fredyhg.com.hospitalquerysystem.models.requests.doctor;


import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.models.requests.appointments.AppointmentGetResponse;
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
