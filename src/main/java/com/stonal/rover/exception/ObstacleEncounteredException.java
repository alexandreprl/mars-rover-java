package com.stonal.rover.exception;

import java.awt.*;

public class ObstacleEncounteredException extends Exception {
    public ObstacleEncounteredException(Point obstaclePosition) {
        super(String.format("Encountered an obstacle in position %d,%d", obstaclePosition.x, obstaclePosition.y));
    }
}
