package dev.fredyhg.com.hospitalquerysystem.exception.appointment;

public class AppointmentAlreadyFinishException extends RuntimeException{
    public AppointmentAlreadyFinishException(String msg){
        super(msg);
    }
}
