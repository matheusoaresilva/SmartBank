package br.com.matheus.smartbank.ms.bankaccount.controller.exception;

import br.com.matheus.smartbank.ms.bankaccount.service.exception.AccountNotAllowedException;
import br.com.matheus.smartbank.ms.bankaccount.service.exception.InsufficientBalanceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<StandardError> InsufficientBalanceException(InsufficientBalanceException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("The withdrawal amount is greater than the balance available for withdrawal!");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
    @ExceptionHandler(AccountNotAllowedException.class)
    public ResponseEntity<StandardError> AccountNotAllowedException(AccountNotAllowedException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("It was not possible to perform this action. " +
                "Please check that the account is activated to perform this action");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
