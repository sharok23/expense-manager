package com.edstem.expensemanager.exception;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {
    private final String entity;

    public InvalidUserException(String entity) {
        super("Invalid " + entity);
        this.entity = entity;
    }
}
