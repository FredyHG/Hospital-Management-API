package dev.fredyhg.com.hospitalquerysystem.dominio.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPutRequest {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate appointmentDate;

    private String drugAllergy;

    private Boolean statusAttended;

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    private String cpfPatient;

}
