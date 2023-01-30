package com.stonal.rover;

import java.awt.*;

public class Rover {
    private final Point position;
    private final CardinalDirection facedDirection;

    public Rover(Point initialStartingPoint, CardinalDirection initialFacedDirection) throws FailedToInitializeRoverException {
        if (initialStartingPoint == null)
            throw new FailedToInitializeRoverException("The initial position provided cannot be null");
        if (initialFacedDirection == null)
            throw new FailedToInitializeRoverException("The faced direction provided cannot be null");
        this.position = initialStartingPoint;
        this.facedDirection = initialFacedDirection;
    }
}
