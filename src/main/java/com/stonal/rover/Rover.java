package com.stonal.rover;

import com.stonal.rover.command.CommandFactory;
import com.stonal.rover.command.exception.CannotExecuteCommandException;
import com.stonal.rover.command.exception.UnknownCommandException;
import com.stonal.rover.exception.CannotCheckForObstacleException;
import com.stonal.rover.exception.FailedToInitializeRoverException;
import com.stonal.rover.exception.ObstacleEncounteredException;
import com.stonal.rover.planet.exception.InvalidPositionOnPlanetException;
import com.stonal.rover.planet.Planet;

import java.awt.*;

public class Rover {
    private Point position;
    private CardinalDirection facedDirection;
    private final CommandFactory commandFactory;
    private final Planet planet;

    public Rover(Point initialStartingPoint, CardinalDirection initialFacedDirection, CommandFactory commandFactory, Planet planet) throws FailedToInitializeRoverException {
        if (initialStartingPoint == null)
            throw new FailedToInitializeRoverException("The initial position provided cannot be null");
        if (initialFacedDirection == null)
            throw new FailedToInitializeRoverException("The faced direction provided cannot be null");
        if (commandFactory == null)
            throw new FailedToInitializeRoverException("The command factory provided cannot be null");
        this.position = initialStartingPoint;
        this.facedDirection = initialFacedDirection;
        this.commandFactory = commandFactory;
        this.planet = planet;
    }

    public void receiveCommands(String commands) throws CannotExecuteCommandException, UnknownCommandException {
        for (char c : commands.toCharArray()) {
            commandFactory.charToCommand(c).execute(this);
        }
    }

    public void moveForward() {
        position = planet.nextPositionInDirection(position, facedDirection);
    }

    public void moveBackward() {
        position = planet.nextPositionInDirection(position, facedDirection.opposite());
    }

    public void rotateLeft() {
        facedDirection = facedDirection.left();
    }

    public void rotateRight() {
        facedDirection = facedDirection.right();
    }

    public void checkForObstacleForward() throws ObstacleEncounteredException, CannotCheckForObstacleException {
        checkForObstacleInDirection(facedDirection);
    }

    public void checkForObstacleBackward() throws ObstacleEncounteredException, CannotCheckForObstacleException {
        checkForObstacleInDirection(facedDirection.opposite());
    }

    private void checkForObstacleInDirection(CardinalDirection direction) throws ObstacleEncounteredException, CannotCheckForObstacleException {
        Point nextPosition = planet.nextPositionInDirection(position, direction);
        try {
            if (planet.hasObstacleInPosition(nextPosition))
                throw new ObstacleEncounteredException(nextPosition);
        } catch (InvalidPositionOnPlanetException e) {
            throw new CannotCheckForObstacleException(e);
        }
    }
}
