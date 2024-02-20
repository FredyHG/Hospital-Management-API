package dev.fredyhg.com.hospitalquerysystem.exception.doctor;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String msg){
        super(msg);
    }
}
