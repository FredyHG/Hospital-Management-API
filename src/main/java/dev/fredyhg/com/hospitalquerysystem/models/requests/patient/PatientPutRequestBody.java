package dev.fredyhg.com.hospitalquerysystem.models.requests.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientPutRequestBody {

    @NotEmpty
    private Long id;

    @NotEmpty(message = "The patients first Name of patient cannot be empty")
    @Length(min = 3, max = 15)
    private String firstName;

    @NotEmpty(message = "The patients last Name of patient cannot be empty")
    @Length(min = 3, max = 15)
    private String lastName;

    @Length(max = 15)
    private String cpf;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;

    @Length(max = 15)
    private String phone;

}
