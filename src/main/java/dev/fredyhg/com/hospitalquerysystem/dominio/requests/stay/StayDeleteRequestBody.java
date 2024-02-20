package dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StayDeleteRequestBody {

    @NotEmpty
    private String crmDoctor;

    @NotEmpty
    private String cpfPatient;
}
