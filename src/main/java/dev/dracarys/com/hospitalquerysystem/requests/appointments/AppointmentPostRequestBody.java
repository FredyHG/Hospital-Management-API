package dev.dracarys.com.hospitalquerysystem.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPostRequestBody {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date appointmentDate;

    private String drugAllergy;

    private Boolean statusAttended;

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    @Pattern(regexp = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}", message = "Cpf mal formatado")
    private String cpfPatient;

}
