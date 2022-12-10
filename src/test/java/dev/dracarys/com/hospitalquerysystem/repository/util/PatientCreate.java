package dev.dracarys.com.hospitalquerysystem.repository.util;

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
}
