package com.peredera.songs.util.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String description = request.getDescription(false);
        String message = ex.getMessage() + " Song not found with id = " + description.split("/")[3];
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, description);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    public ResponseEntity<MessageExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MessageExceptionHandler("This song was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        String description = request.getDescription(false);
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(), description);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserExistException() {
        String message = "User with this email already exists";
        return new ResponseEntity<>(new MessageExceptionHandler(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleIncorrectLogin(WebRequest request) {
        String description = request.getDescription(false);
        String message = "The username or password you entered is incorrect";
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, description);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AuthenticationException.class, ExpiredJwtException.class })
    public ResponseEntity<?> handleAuthenticationException(WebRequest request) {
        String description = request.getDescription(false);
        String message = "Authentication failed";
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, description);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleAccessException(WebRequest request) {
        String description = request.getDescription(false);
        String message = "Access denied. You are not allowed to use it";
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, description);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        String message = ex.getClass() + ": " + ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    private static class MessageExceptionHandler {
        private String message;
    }
}
