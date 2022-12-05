package dev.dracarys.com.hospitalquerysystem.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPutRequestBody {

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
