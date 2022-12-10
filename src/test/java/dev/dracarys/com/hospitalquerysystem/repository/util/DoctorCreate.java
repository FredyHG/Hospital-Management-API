package dev.dracarys.com.hospitalquerysystem.repository.util;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;

import java.util.Collections;

public class DoctorCreate {


    public static Doctor createValidDoctor(){
        return Doctor.builder()
                .firstName("Fredy")
                .lastName("Gomes")
                .crm("123")
                .appointments(Collections.emptyList())
                .stay(Collections.emptyList())
                .build();
    }

}
