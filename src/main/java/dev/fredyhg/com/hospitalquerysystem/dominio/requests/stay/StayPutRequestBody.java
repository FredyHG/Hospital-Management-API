package dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.fredyhg.com.hospitalquerysystem.enums.StatusStay;
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
public class StayPutRequestBody {

    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate stayDate;

    private String drugAllergy;

    private String description;

    private StatusStay status;

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    private String cpfPatient;




}
