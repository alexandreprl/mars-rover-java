package com.stonal.rover.command;

import com.stonal.rover.Rover;
import com.stonal.rover.command.exception.CannotExecuteCommandException;
import com.stonal.rover.exception.CannotCheckForObstacleException;
import com.stonal.rover.exception.ObstacleEncounteredException;

public class MoveForwardCommand implements Command {
    @Override
    public void execute(Rover rover) throws CannotExecuteCommandException {
        try {
            rover.checkForObstacleForward();
        } catch (CannotCheckForObstacleException | ObstacleEncounteredException e) {
            throw new CannotExecuteCommandException(e);
        }
        rover.moveForward();
    }
}
