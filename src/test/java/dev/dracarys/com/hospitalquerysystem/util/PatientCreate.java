package dev.dracarys.com.hospitalquerysystem.util;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;

import java.util.Collections;

public class PatientCreate {



    public static Patients createValidPatient(){
        return Patients.builder()
                .firstName("Arthur")
                .lastName("Romas")
                .stays(Collections.emptyList())
                .appointments(Collections.emptyList())
                .cpf("123")
                .build();
    }

    public static Patients createValidPatientForStayTest(){
        return Patients.builder()
                .firstName("Arthur")
                .lastName("Romas")
                .stays(Collections.emptyList())
                .appointments(Collections.emptyList())
                .cpf("1234")
                .build();
    }
}
