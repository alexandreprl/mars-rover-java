package com.stonal.rover.command;

import com.stonal.rover.Rover;

public interface Command {
    public void execute(Rover rover);
}
