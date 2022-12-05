package dev.dracarys.com.hospitalquerysystem.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPostRequestBody {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate appointmentDate;

    private String drugAllergy;

    private Boolean statusAttended;

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "Cpf mal formatado")
    private String cpfPatient;

}
