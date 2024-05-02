package org.example.exception;

import org.example.exception.generic.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(
            NotFoundException ex,
            WebRequest request
    ) {
        var status = HttpStatus.NOT_FOUND;
        var errorDto = new ErrorResponseDto(
                LocalDateTime.now(),
                status.toString(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getContextPath()
        );
        return errorDto.asResponseEntity(status);
    }

    record ErrorResponseDto(
            LocalDateTime timestamp,
            String status,
            String error,
            String message,
            String path
    ) {
        public ResponseEntity<ErrorResponseDto> asResponseEntity(HttpStatusCode status) {
            return new ResponseEntity<>(this, status);
        }
    }
}
