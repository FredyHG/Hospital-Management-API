package dev.fredyhg.com.hospitalquerysystem.exception.appointment;

public class AppointmentNotFound extends RuntimeException{
    public AppointmentNotFound(String msg){
        super(msg);
    }
}
