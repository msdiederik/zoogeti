package com.sogeti.zoogeti.feedingapp.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, String column, String value) {
        super("Could not find " + entity + " with " + column + ": " + value );
    }
}
