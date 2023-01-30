package com.stonal.rover;

import com.stonal.rover.command.CommandFactory;
import com.stonal.rover.command.exception.UnknownCommandException;
import com.stonal.rover.exception.FailedToInitializeRoverException;

import java.awt.*;

public class Rover {
    private final Point position;
    private CardinalDirection facedDirection;
    private final CommandFactory commandFactory;

    public Rover(Point initialStartingPoint, CardinalDirection initialFacedDirection, CommandFactory commandFactory) throws FailedToInitializeRoverException {
        this.commandFactory = commandFactory;
        if (initialStartingPoint == null)
            throw new FailedToInitializeRoverException("The initial position provided cannot be null");
        if (initialFacedDirection == null)
            throw new FailedToInitializeRoverException("The faced direction provided cannot be null");
        if (commandFactory == null)
            throw new FailedToInitializeRoverException("The command factory provided cannot be null");
        this.position = initialStartingPoint;
        this.facedDirection = initialFacedDirection;
    }

    public void receiveCommands(String commands) throws UnknownCommandException {
        for (char c : commands.toCharArray()) {
            commandFactory.charToCommand(c).execute(this);
        }
    }

    public void moveForward() {
        switch (facedDirection) {
            case NORTH -> position.y++;
            case SOUTH -> position.y--;
            case EAST -> position.x++;
            case WEST -> position.x--;
        }
    }

    public void moveBackward() {
        switch (facedDirection) {
            case NORTH -> position.y--;
            case SOUTH -> position.y++;
            case EAST -> position.x--;
            case WEST -> position.x++;
        }
    }

    public void rotateLeft() {
        facedDirection = switch (facedDirection) {
            case NORTH -> CardinalDirection.WEST;
            case WEST -> CardinalDirection.SOUTH;
            case SOUTH -> CardinalDirection.EAST;
            case EAST -> CardinalDirection.NORTH;
        };
    }

    public void rotateRight() {
        facedDirection = switch (facedDirection) {
            case NORTH -> CardinalDirection.EAST;
            case EAST -> CardinalDirection.SOUTH;
            case SOUTH -> CardinalDirection.WEST;
            case WEST -> CardinalDirection.NORTH;
        };
    }
}
