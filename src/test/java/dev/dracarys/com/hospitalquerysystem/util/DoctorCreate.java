package dev.dracarys.com.hospitalquerysystem.util;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorDtoViewAll;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorGetReturnObject;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPutRequestBody;

import java.util.Collections;

public class DoctorCreate {


    public static Doctor createValidDoctor() {
        return Doctor.builder()
                .firstName("Fredy")
                .lastName("Gomes")
                .crm("123")
                .appointments(Collections.emptyList())
                .stay(Collections.emptyList())
                .build();
    }


    public static DoctorDtoViewAll createValidDoctorDTO() {
        return DoctorDtoViewAll.builder()

                .firstName("Pedro")
                .lastName("Ferraz")
                .crm("123")
                .appointments(Collections.emptyList())
                .stays(Collections.emptyList())
                .build();
    }

    public static DoctorGetReturnObject createValidDoctorDtoView() {
        return DoctorGetReturnObject.builder()
                .staysView(Collections.emptyList())
                .appointmentsView(Collections.emptyList())
                .firstName("Pedro")
                .lastName("Ferraz")
                .crm("123")
                .appointments(Collections.emptyList())
                .stays(Collections.emptyList())
                .build();
    }

    public static DoctorPostRequestBody createDoctorToBeSaved() {

        return DoctorPostRequestBody.builder()
                .crm("123")
                .firstName("Mateus")
                .lastName("Silva")
                .build();
    }

    public static DoctorPutRequestBody createPutDoctorToBeSaved() {

        return DoctorPutRequestBody.builder()
                .id(1L)
                .crm("123")
                .firstName("Mateus")
                .lastName("Silva")
                .build();
    }

}
