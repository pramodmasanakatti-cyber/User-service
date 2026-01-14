package com.userservice.exception;

import com.userservice.dto.response.ErrorResponseDTO;
import jakarta.transaction.InvalidTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(RuntimeException exception) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                Map.of(exception instanceof UserNotFoundException ? "User" : "Wallet", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateEmailException(RuntimeException exception) {
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                Map.of("email",exception.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }
}
