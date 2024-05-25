package dev.fredyhg.com.hospitalquerysystem.exception.user;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String msg) {
        super(msg);
    }
}
