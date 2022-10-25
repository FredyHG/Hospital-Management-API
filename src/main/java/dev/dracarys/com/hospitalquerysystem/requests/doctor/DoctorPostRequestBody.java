package dev.dracarys.com.hospitalquerysystem.requests.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPostRequestBody {
    @Length(min = 3,max = 16)
    @NotEmpty
    private String firstName;
    @Length(min = 3,max = 16)
    @NotEmpty
    private String lastName;
    @Length(max = 16)
    @NotEmpty
    private Long crm;
}
