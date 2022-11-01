package dev.dracarys.com.hospitalquerysystem.requests.stay;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.dracarys.com.hospitalquerysystem.enums.StatusStay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StayPostRequestBody {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date stayDate;
    @NotEmpty(message = "The field drug allergy cannot be empty")
    private String drugAllergy;
    private String description;
    @NotNull
    private StatusStay status;
    @NotEmpty(message = "The doctor crm cannot be empty")
    private String crmDoctor;
    @NotEmpty(message = "The patient cpf cannot be empty")
    private String cpfPatient;

}