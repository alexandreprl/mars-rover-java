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

    public CardinalDirection left() {
        return switch (this) {
            case NORTH -> CardinalDirection.WEST;
            case WEST -> CardinalDirection.SOUTH;
            case SOUTH -> CardinalDirection.EAST;
            case EAST -> CardinalDirection.NORTH;
        };
    }

    public CardinalDirection right() {
        return switch (this) {
            case NORTH -> CardinalDirection.EAST;
            case EAST -> CardinalDirection.SOUTH;
            case SOUTH -> CardinalDirection.WEST;
            case WEST -> CardinalDirection.NORTH;
        };
    }
}
