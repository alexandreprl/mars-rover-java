package com.stonal.rover.command;

import com.stonal.rover.Rover;

public class RotateRightCommand implements Command {
    @Override
    public void execute(Rover rover) {
        rover.rotateRight();
    }
}
