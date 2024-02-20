package dev.fredyhg.com.hospitalquerysystem.exception.doctor;

public class DoctorAlreadyExistsException extends RuntimeException{
    public DoctorAlreadyExistsException(String msg){
        super(msg);
    }
}
