package dev.dracarys.com.hospitalquerysystem.requests.appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwitchPatientStatementRequestBody {

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    private String cpfPatient;


}
