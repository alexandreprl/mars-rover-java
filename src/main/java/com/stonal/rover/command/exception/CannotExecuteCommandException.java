package com.stonal.rover.command.exception;

public class CannotExecuteCommandException extends Exception {

    public CannotExecuteCommandException(Exception e) {
        super(e);
    }
}
