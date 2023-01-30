package com.stonal.rover

import com.stonal.rover.command.Command
import com.stonal.rover.command.CommandFactory
import com.stonal.rover.command.exception.UnknownCommandException
import com.stonal.rover.exception.FailedToInitializeRoverException
import spock.lang.Specification
import spock.lang.Unroll

import java.awt.*

@Unroll
class RoverTest extends Specification {
    def "A Rover can be initialized with a starting point and the direction it is facing"() {
        when:
        def rover = new Rover(new Point(x, y), facedDirection, new CommandFactory())

        then:
        rover.position.x == x
        rover.position.y == y
        rover.facedDirection == facedDirection

        where:
        x | y | facedDirection
        0 | 0 | CardinalDirection.NORTH
        0 | 0 | CardinalDirection.SOUTH
        0 | 0 | CardinalDirection.EAST
        0 | 0 | CardinalDirection.WEST
        0 | 1 | CardinalDirection.NORTH
        1 | 1 | CardinalDirection.NORTH
    }

    def "When a rover is initialized with invalid values then an exception is thrown"() {
        when:
        new Rover(point, facedDirection, commandFactory)

        then:
        def e = thrown(FailedToInitializeRoverException)
        e.message == exceptionMessage

        where:
        point       | facedDirection          | commandFactory       | exceptionMessage
        null        | CardinalDirection.NORTH | new CommandFactory() | "The initial position provided cannot be null"
        new Point() | null                    | new CommandFactory() | "The faced direction provided cannot be null"
        new Point() | CardinalDirection.NORTH | null                 | "The command factory provided cannot be null"
    }

    def "When an array of commands is received then the commands are executed"() {
        given:
        def commandA = Mock(Command)
        def commandB = Mock(Command)
        def commandFactory = Mock(CommandFactory)
        commandFactory.charToCommand(_) >> {
            char c ->
                switch (c) {
                    case 'a': return commandA
                    case 'b': return commandB
                }
        }

        and:
        def rover = new Rover(new Point(0, 0), CardinalDirection.NORTH, commandFactory)

        when:
        rover.receiveCommands("ab")

        then:
        1 * commandA.execute(rover)
        1 * commandB.execute(rover)
    }

    def "When an unknown command is received then an exception is thrown"() {
        given:
        def commandFactory = Mock(CommandFactory)
        commandFactory.charToCommand(_) >> {
            char c ->
                throw new UnknownCommandException();
        }

        and:
        def rover = new Rover(new Point(0, 0), CardinalDirection.NORTH, commandFactory)

        when:
        rover.receiveCommands("ab")

        then:
        thrown(UnknownCommandException)
    }

    def "moveForward"() {
        given:
        def rover = new Rover(new Point(0, 0), facedDirection, new CommandFactory())

        when:
        rover.moveForward()

        then:
        rover.position.x == expectedX
        rover.position.y == expectedY

        where:
        expectedX | expectedY | facedDirection
        0         | 1         | CardinalDirection.NORTH
        0         | -1        | CardinalDirection.SOUTH
        1         | 0         | CardinalDirection.EAST
        -1        | 0         | CardinalDirection.WEST
    }

    def "moveBackward"() {
        given:
        def rover = new Rover(new Point(0, 0), facedDirection, new CommandFactory())

        when:
        rover.moveBackward()

        then:
        rover.position.x == expectedX
        rover.position.y == expectedY

        where:
        expectedX | expectedY | facedDirection
        0         | 1         | CardinalDirection.SOUTH
        0         | -1        | CardinalDirection.NORTH
        1         | 0         | CardinalDirection.WEST
        -1        | 0         | CardinalDirection.EAST
    }
}