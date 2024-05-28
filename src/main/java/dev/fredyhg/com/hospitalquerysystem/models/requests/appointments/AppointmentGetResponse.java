package dev.fredyhg.com.hospitalquerysystem.models.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.fredyhg.com.hospitalquerysystem.models.Patient;
import dev.fredyhg.com.hospitalquerysystem.models.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGetResponse {

    @JsonIgnore
    private Long patientId;

    @JsonIgnore
    private Long doctorId;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date appointmentDate;

    private String patientName;

    private String doctorName;

    private Boolean patientAttended;

    private String drugAllergy;

    @JsonIgnore
    private Doctor doctor;

    @JsonIgnore
    private Patient patient;

}
