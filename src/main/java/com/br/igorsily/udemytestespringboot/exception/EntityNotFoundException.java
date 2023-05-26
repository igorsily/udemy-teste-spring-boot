package com.br.igorsily.udemytestespringboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String exception) {
        super(exception);
    }

    public EntityNotFoundException(String exception, Throwable cause) {
        super(exception, cause);
    }
}
