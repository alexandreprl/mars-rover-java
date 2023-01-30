package com.stonal.rover.command;

import com.stonal.rover.command.exception.CannotExecuteCommandException;
import com.stonal.rover.command.exception.UnknownCommandException;

public class CommandFactory {
    public Command charToCommand(char character) throws CannotExecuteCommandException, UnknownCommandException {
        return switch (character) {
            case 'f' -> new MoveForwardCommand();
            case 'b' -> new MoveBackwardCommand();
            case 'l' -> new RotateLeftCommand();
            case 'r' -> new RotateRightCommand();
            default -> throw new UnknownCommandException();
        };
    }
}
