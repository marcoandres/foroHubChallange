package com.alura.foroHub.foroHubChallange.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class TratadorDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity tratarError406() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream()
                .map(error -> new DatosErrorValidacion(error.getField(), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> tratarErrorGenerico(ResponseStatusException e) {
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
        String message = e.getReason() != null ? e.getReason() : "Ocurrió un error inesperado. Por favor, inténtalo de nuevo más tarde.";
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }


    private static class ErrorResponse {
        private final int status;
        private final String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }


    private record DatosErrorValidacion(String campo, String error) {
    }
}
