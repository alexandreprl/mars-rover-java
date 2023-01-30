package com.stonal.rover.command;

import com.stonal.rover.Rover;
import com.stonal.rover.command.exception.CannotExecuteCommandException;

public interface Command {
    void execute(Rover rover) throws CannotExecuteCommandException;
}
