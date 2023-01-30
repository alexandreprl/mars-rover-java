package com.stonal.rover.exception;

import com.stonal.rover.planet.exception.InvalidPositionOnPlanetException;

public class CannotCheckForObstacleException extends Exception {
    public CannotCheckForObstacleException(InvalidPositionOnPlanetException e) {
        super(e);
    }
}
