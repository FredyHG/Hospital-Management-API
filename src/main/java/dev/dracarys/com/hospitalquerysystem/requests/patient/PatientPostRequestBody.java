package dev.dracarys.com.hospitalquerysystem.requests.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.dracarys.com.hospitalquerysystem.validation.ValidCPF;
import dev.dracarys.com.hospitalquerysystem.validation.ValidPhone;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PatientPostRequestBody {

    @NotEmpty(message = "The patients first Name of patient cannot be empty")
    @Length(min = 3, max = 15)
    @Schema(example = "Arthur")
    private String firstName;

    @NotEmpty(message = "The patients last Name of patient cannot be empty")
    @Schema(example = "Silva")
    @Length(min = 3, max = 15)
    private String lastName;

//    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "Invalid CPF")
    @ValidCPF
    @Schema(example = "111.222.333-12")
    private String cpf;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(example = "2004-02-20")
    private LocalDate birthdate;

    @ValidPhone
    @Schema(example = "(11)998209934")
    private String phone;

}
