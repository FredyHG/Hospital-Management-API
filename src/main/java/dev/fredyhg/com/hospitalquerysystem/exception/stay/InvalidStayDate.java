package dev.fredyhg.com.hospitalquerysystem.exception.stay;

public class InvalidStayDate extends RuntimeException{
    public InvalidStayDate(String msg){
        super(msg);
    }
}
