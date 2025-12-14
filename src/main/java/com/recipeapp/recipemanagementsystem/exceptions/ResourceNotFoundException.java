package com.recipeapp.recipemanagementsystem.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s δεν βρέθηκε με %s: %s", resource, field, value));
    }
}
