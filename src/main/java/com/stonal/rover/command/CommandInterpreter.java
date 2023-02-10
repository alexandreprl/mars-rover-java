package com.stonal.rover.command;

import com.stonal.rover.Rover;
import com.stonal.rover.command.exception.CannotExecuteCommandException;
import com.stonal.rover.command.exception.UnknownCommandException;

public class CommandInterpreter {
    private final CommandFactory commandFactory;

    public CommandInterpreter(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void interpret(String commands, Rover rover) throws CannotExecuteCommandException, UnknownCommandException {
        for (char c : commands.toCharArray()) {
            rover.execute(commandFactory.charToCommand(c));
        }
    }
}
