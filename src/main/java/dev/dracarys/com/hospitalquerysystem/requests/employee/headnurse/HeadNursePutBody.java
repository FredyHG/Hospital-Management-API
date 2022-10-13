package dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeadNursePutBody {
    private Long id;
    @Length(min = 3,max = 16)
    private String firstName;
    @Length(min = 3,max = 16)
    private String lastName;
}
