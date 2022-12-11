package dev.dracarys.com.hospitalquerysystem.util;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;

import java.util.Date;

public class CreateAppointment {

    public static Appointments createValidAppointment(Doctor doctor, Patients patient){
        return Appointments.builder()
                .appointmentDate(new Date())
                .patient(patient)
                .doctor(doctor)
                .patientAttended(false)
                .drugAllergy("Pinga")
                .build();
    }



}
