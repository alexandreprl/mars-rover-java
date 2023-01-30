package com.stonal.rover.command;

import com.stonal.rover.command.exception.UnknownCommandException;

public class CommandFactory {
    public Command charToCommand(char character) throws UnknownCommandException {
        return switch (character) {
            case 'f' -> new MoveForwardCommand();
            case 'b' -> new MoveBackwardCommand();
            default -> throw new UnknownCommandException();
        };
    }
}
