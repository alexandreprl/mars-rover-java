package com.stonal.rover.command;

import com.stonal.rover.Rover;

public interface Command {
    void execute(Rover rover);
}
