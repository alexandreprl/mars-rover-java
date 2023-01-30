package com.stonal.rover.planet;

import com.stonal.rover.CardinalDirection;
import com.stonal.rover.planet.exception.InvalidPlanetSizeException;
import com.stonal.rover.planet.exception.InvalidPositionOnPlanetException;

import java.awt.*;

public class Planet {
    private final int width;
    private final int height;
    private final boolean[][] obstaclesMap;

    public Planet(int width, int height) throws InvalidPlanetSizeException {
        if (width <= 0 || height <= 0)
            throw new InvalidPlanetSizeException();
        this.width = width;
        this.height = height;
        this.obstaclesMap = new boolean[width][height];
    }

    public void addObstacle(Point obstaclePosition) throws InvalidPositionOnPlanetException {
        if (!positionIsValid(obstaclePosition))
            throw new InvalidPositionOnPlanetException(obstaclePosition);
        obstaclesMap[obstaclePosition.x][obstaclePosition.y] = true;
    }

    private boolean positionIsValid(Point position) {
        return position.x >= 0 && position.x < width && position.y >= 0 && position.y < height;
    }

    public Point nextPositionInDirection(Point position, CardinalDirection direction) {
        Point newPosition = new Point(position);
        switch (direction) {
            case NORTH -> {
                newPosition.y = (newPosition.y + 1) % height;
            }
            case SOUTH -> {
                newPosition.y = newPosition.y - 1;
                while (newPosition.y < 0) newPosition.y += height;
            }
            case EAST -> {
                newPosition.x = (newPosition.x + 1) % width;
            }
            case WEST -> {
                newPosition.x = newPosition.x - 1;
                while (newPosition.x < 0) newPosition.x += width;
            }
        }
        return newPosition;
    }

    public boolean hasObstacleInPosition(Point position) throws InvalidPositionOnPlanetException {
        if (!positionIsValid(position))
            throw new InvalidPositionOnPlanetException(position);
        return this.obstaclesMap[position.x][position.y];
    }
}
