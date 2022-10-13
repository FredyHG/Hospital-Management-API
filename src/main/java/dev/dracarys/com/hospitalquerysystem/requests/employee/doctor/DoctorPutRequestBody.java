package dev.dracarys.com.hospitalquerysystem.requests.employee.doctor;

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
public class DoctorPutRequestBody {
    @NotEmpty
    private Long id;
    @Length(min = 3,max = 16)
    private String firstName;
    @Length(min = 3,max = 16)
    private String lastName;
    @Length(max = 16)
    private String crm;
}
