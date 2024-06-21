package com.backend.clinica_odontologica.exceptions;
//aqui se modifico el extends ...
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
