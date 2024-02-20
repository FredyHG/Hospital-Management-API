package dev.fredyhg.com.hospitalquerysystem.exception.patient;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String msg){
        super(msg);
    }
}
