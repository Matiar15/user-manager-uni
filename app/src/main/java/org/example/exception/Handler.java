package org.example.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.exception.generic.BadRequestException;
import org.example.exception.generic.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        var status = NOT_FOUND;
        var errorDto = new ErrorResponseDto(
                LocalDateTime.now(),
                status.toString(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return errorDto.asResponseEntity(status);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        var status = BAD_REQUEST;
        var errorDto = new ErrorResponseDto(
                LocalDateTime.now(),
                status.toString(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
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
