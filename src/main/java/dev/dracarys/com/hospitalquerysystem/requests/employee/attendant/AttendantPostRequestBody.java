package dev.dracarys.com.hospitalquerysystem.requests.employee.attendant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendantPostRequestBody {

    @Length(min = 3,max = 16)
    private String firstName;
    @Length(min = 3,max = 16)
    private String lastName;
}
