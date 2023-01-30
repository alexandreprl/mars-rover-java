package com.stonal.rover;

public enum CardinalDirection {
    NORTH, SOUTH, EAST, WEST;

    public CardinalDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }
}
