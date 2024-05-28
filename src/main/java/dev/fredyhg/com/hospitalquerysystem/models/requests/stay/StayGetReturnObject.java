package dev.fredyhg.com.hospitalquerysystem.models.requests.stay;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.fredyhg.com.hospitalquerysystem.enums.StatusStay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StayGetReturnObject {

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date stayDate;

    private String description;

    private String patientName;

    private String doctorName;

    private StatusStay statusStay;

    private String drugAllergy;

}
