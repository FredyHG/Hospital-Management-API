package dev.fredyhg.com.hospitalquerysystem.exception.user;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String msg) {
        super(msg);
    }
}
