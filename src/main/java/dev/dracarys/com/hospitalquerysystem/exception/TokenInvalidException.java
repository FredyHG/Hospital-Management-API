package dev.dracarys.com.hospitalquerysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Teste")
public class TokenInvalidException extends RuntimeException {

}
