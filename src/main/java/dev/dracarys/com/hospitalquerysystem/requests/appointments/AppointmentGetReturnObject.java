package dev.dracarys.com.hospitalquerysystem.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGetReturnObject {

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
    private Patients patients;

}
