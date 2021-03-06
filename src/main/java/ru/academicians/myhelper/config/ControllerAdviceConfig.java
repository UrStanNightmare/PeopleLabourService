package ru.academicians.myhelper.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.ErrorResponse;

@RestControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidRequestArgumentException(
            Exception ex,
            WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            ItemNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            Exception ex,
            WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception ex, WebRequest req) {

        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
