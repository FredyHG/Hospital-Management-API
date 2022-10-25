package dev.dracarys.com.hospitalquerysystem.requests.stay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.enums.StatusStay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StayDto {


    @JsonIgnore
    private Long patientId;
    @JsonIgnore
    private Long doctorId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date stayDate;
    private String description;
    private String patientName;
    private String doctorName;
    private StatusStay statusStay;
    private String drugAllergy;
    @JsonIgnore
    private Doctor doctor;
    @JsonIgnore
    private Patients patients;

}
