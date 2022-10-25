package dev.dracarys.com.hospitalquerysystem.requests.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsPostRequestBody {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date appointmentDate;
    private String drugAllergy;
    private Boolean statusAttended;
    @NotEmpty
    private Long crmDoctor;
    @NotEmpty
    private String cpf;

}
