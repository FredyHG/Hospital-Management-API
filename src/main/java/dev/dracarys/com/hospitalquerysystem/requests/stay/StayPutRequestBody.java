package dev.dracarys.com.hospitalquerysystem.requests.stay;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.dracarys.com.hospitalquerysystem.enums.StatusStay;
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
public class StayPutRequestBody {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date stayDate;

    private String drugAllergy;

    private String description;

    private StatusStay status;

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    private String cpfPatient;




}
