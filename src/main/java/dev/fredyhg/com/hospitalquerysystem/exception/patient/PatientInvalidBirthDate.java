package dev.fredyhg.com.hospitalquerysystem.exception.patient;

public class PatientInvalidBirthDate extends RuntimeException {
    public PatientInvalidBirthDate(String msg) {
        super(msg);
    }
}
