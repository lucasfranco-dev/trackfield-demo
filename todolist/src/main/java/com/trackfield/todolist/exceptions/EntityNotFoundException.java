package com.trackfield.todolist.exceptions;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
