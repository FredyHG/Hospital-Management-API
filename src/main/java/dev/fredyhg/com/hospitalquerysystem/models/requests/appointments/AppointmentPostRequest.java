package dev.fredyhg.com.hospitalquerysystem.models.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPostRequest {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate appointmentDate;

    private String drugAllergy;

    private Boolean statusAttended;

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "invalid format {field = 'CPF'")
    private String cpfPatient;

}
