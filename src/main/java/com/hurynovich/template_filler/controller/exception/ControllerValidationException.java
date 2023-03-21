package com.hurynovich.template_filler.controller.exception;

public class ControllerValidationException extends RuntimeException {

    public ControllerValidationException(final String message) {
        super(message);
    }
}
