package com.microservices.microservices.Exceptions;

import com.microservices.microservices.Exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

    // Validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        System.out.println(">>> Validation triggered for fields: " + ex.getBindingResult().getFieldErrors());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                fieldErrors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Illegal arguments, null pointers, etc.
//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCommonRuntimeExceptions(Exception ex) {
        Map<String, String> error = Map.of("error", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Runtime error",
                error
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Catch-all for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        Map<String, String> error = Map.of("error", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error occurred",
                error
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                Map.of("error", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
