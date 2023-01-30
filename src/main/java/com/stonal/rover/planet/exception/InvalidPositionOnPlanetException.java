package com.stonal.rover.planet.exception;

import java.awt.*;

public class InvalidPositionOnPlanetException extends Exception {
    public InvalidPositionOnPlanetException(Point position) {
        super(String.format("The position %d,%d is not valid on this planet", position.x, position.y));
    }
}
