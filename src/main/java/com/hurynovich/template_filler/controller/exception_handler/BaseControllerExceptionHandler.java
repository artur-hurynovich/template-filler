package com.hurynovich.template_filler.controller.exception_handler;

import com.hurynovich.template_filler.controller.exception.ControllerValidationException;
import com.hurynovich.template_filler.service.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class BaseControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(final NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(ControllerValidationException.class)
    public ResponseEntity<Object> handleControllerValidationException(final ControllerValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }
}
