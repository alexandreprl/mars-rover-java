package com.stonal.rover.planet;

import com.stonal.rover.CardinalDirection;
import com.stonal.rover.planet.exception.InvalidPlanetSizeException;

import java.awt.*;

public class Planet {
    private final int width;
    private final int height;

    public Planet(int width, int height) throws InvalidPlanetSizeException {
        if (width <= 0 || height <= 0)
            throw new InvalidPlanetSizeException();
        this.width = width;
        this.height = height;
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
}
