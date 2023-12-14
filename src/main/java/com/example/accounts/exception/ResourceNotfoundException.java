package com.example.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotfoundException extends RuntimeException{

    public ResourceNotfoundException(String resourceName, String fieldName, String fieldValue) {
        super("""
                $x not found with the given input data $y: $z
                """.formatted(resourceName, fieldName, fieldValue));
    }
}
