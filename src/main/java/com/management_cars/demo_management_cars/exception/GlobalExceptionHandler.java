package com.management_cars.demo_management_cars.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                request,
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(
            NoHandlerFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Endpoint não encontrado: {} {}", request.getMethod(), request.getRequestURI());

        ErrorResponse error = new ErrorResponse(
                request,
                HttpStatus.NOT_FOUND,
                "Endpoint não encontrado"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        log.warn("Método não permitido: {} {}", request.getMethod(), request.getRequestURI());

        ErrorResponse error = new ErrorResponse(
                request,
                HttpStatus.METHOD_NOT_ALLOWED,
                "Método HTTP não permitido para este endpoint"
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        log.warn("Erro de validação: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                request,
                HttpStatus.BAD_REQUEST,
                "Dados inválidos",
                ex.getBindingResult(),
                messageSource
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        log.warn("BadRequest: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                request,
                HttpStatus.BAD_REQUEST,
                ex.getMessage()

        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        log.error("Violação de integridade de dados", ex);

        ErrorResponse error = new ErrorResponse(
                request,
                HttpStatus.BAD_REQUEST,
                "Violação de integridade de dados"
        );

        return ResponseEntity.badRequest().body(error);
    }
}
