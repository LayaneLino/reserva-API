package com.example.reservas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalStateException.class, RuntimeException.class})
    public ResponseEntity<Object> handleBusinessExceptions(RuntimeException ex) {
        String msg = ex.getMessage().toLowerCase();

        if (msg.contains("não encontrado")) {
            return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        if (msg.contains("não é possível") || msg.contains("deve ser") || msg.contains("só é permitido") || msg.contains("já possui")) {
            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String mensagem) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", status.value());
        corpo.put("message", mensagem);
        return new ResponseEntity<>(corpo, status);
    }
}