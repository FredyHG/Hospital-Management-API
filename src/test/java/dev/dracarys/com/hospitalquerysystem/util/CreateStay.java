package dev.dracarys.com.hospitalquerysystem.util;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import dev.dracarys.com.hospitalquerysystem.enums.StatusStay;

import java.util.Date;

public class CreateStay {

    public static Stay createValidStay(Doctor doctor, Patients patient){
        return Stay.builder()
                .stayDate(new Date())
                .drugAllergy("Pinga Azul")
                .description("please help him")
                .status(StatusStay.ADMITTED)
                .doctor(doctor)
                .patient(patient)
                .build();

    }

    public static Stay createValidStayForStayTest(Doctor doctor, Patients patient){
        return Stay.builder()
                .stayDate(new Date())
                .drugAllergy("Pinga Rosa")
                .description("please help him :(")
                .status(StatusStay.ADMITTED)
                .doctor(doctor)
                .patient(patient)
                .build();

    }
}
