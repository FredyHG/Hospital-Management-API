package dev.fredyhg.com.hospitalquerysystem.exception.appointment;

public class AppointmentPendingAlreadyExists extends RuntimeException{
    public AppointmentPendingAlreadyExists(String msg){
        super(msg);
    }
}
