package com.stonal.rover.command;

import com.stonal.rover.Rover;

public class MoveForwardCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.moveForward();
    }
}
