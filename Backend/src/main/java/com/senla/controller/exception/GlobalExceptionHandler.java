package com.senla.controller.exception;

import com.senla.exception.DaoException;
import com.senla.exception.InternalException;
import com.senla.exception.NotFoundByIdException;
import com.senla.util.error.MapErrorResponse;
import com.senla.util.error.MessageErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<MessageErrorResponse> handleDaoException(DaoException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                new MessageErrorResponse(HttpStatus.NOT_FOUND.value(), "DaoException" + e.getMessage(), System.currentTimeMillis()),
                    HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InternalException.class)
    public ResponseEntity<MessageErrorResponse> handleInternalException(InternalException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                new MessageErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "InternalException" + e.getMessage(), System.currentTimeMillis()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundByIdException.class)
    public ResponseEntity<MessageErrorResponse> handleNotFoundByIdException(NotFoundByIdException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                new MessageErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis()),
                    HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MapErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new MapErrorResponse(HttpStatus.BAD_REQUEST.value(), errors, System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageErrorResponse> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new MessageErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(new MessageErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleIntegrityViolationException(DataIntegrityViolationException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(new MessageErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<?> handleServletException(ServletException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(new MessageErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleNotCaughtException(Exception e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(new MessageErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
