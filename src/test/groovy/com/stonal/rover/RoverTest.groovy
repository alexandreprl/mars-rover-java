package com.stonal.rover

import com.stonal.rover.command.Command
import com.stonal.rover.command.CommandFactory
import com.stonal.rover.command.exception.UnknownCommandException
import com.stonal.rover.exception.FailedToInitializeRoverException
import com.stonal.rover.planet.Planet
import spock.lang.Specification
import spock.lang.Unroll

import java.awt.*

@Unroll
class RoverTest extends Specification {
    def "A Rover can be initialized with a starting point and the direction it is facing"() {
        when:
        def rover = createRover(new Point(x, y), facedDirection, new CommandFactory())

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
        createRover(point, facedDirection, commandFactory)

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
        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, commandFactory)

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
        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, commandFactory)

        when:
        rover.receiveCommands("ab")

        then:
        thrown(UnknownCommandException)
    }

    def "moveForward"() {
        given:
        def rover = createRover(new Point(0, 0), initialFacedDirection)

        when:
        rover.moveForward()

        then:
        rover.position.x == expectedX
        rover.position.y == expectedY

        where:
        expectedX | expectedY | initialFacedDirection
        0         | 1         | CardinalDirection.NORTH
        0         | 9         | CardinalDirection.SOUTH
        1         | 0         | CardinalDirection.EAST
        9         | 0         | CardinalDirection.WEST
    }

    def "moveBackward"() {
        given:
        def rover = createRover(new Point(0, 0), initialFacedDirection)

        when:
        rover.moveBackward()

        then:
        rover.position.x == expectedX
        rover.position.y == expectedY

        where:
        expectedX | expectedY | initialFacedDirection
        0         | 1         | CardinalDirection.SOUTH
        0         | 9         | CardinalDirection.NORTH
        1         | 0         | CardinalDirection.WEST
        9         | 0         | CardinalDirection.EAST
    }

    def "rotateLeft"() {
        given:
        def rover = createRover(new Point(0, 0), initialFacedDirection)

        when:
        rover.rotateLeft()

        then:
        rover.facedDirection == expectedFacedDirection

        where:
        initialFacedDirection   | expectedFacedDirection
        CardinalDirection.NORTH | CardinalDirection.WEST
        CardinalDirection.WEST  | CardinalDirection.SOUTH
        CardinalDirection.SOUTH | CardinalDirection.EAST
        CardinalDirection.EAST  | CardinalDirection.NORTH
    }

    def "rotateRight"() {
        given:
        def rover = createRover(new Point(0, 0), initialFacedDirection)

        when:
        rover.rotateRight()

        then:
        rover.facedDirection == expectedFacedDirection

        where:
        initialFacedDirection   | expectedFacedDirection
        CardinalDirection.NORTH | CardinalDirection.EAST
        CardinalDirection.EAST  | CardinalDirection.SOUTH
        CardinalDirection.SOUTH | CardinalDirection.WEST
        CardinalDirection.WEST  | CardinalDirection.NORTH
    }

    def createRover(position = new Point(0, 0), facedDirection = CardinalDirection.NORTH, commandFactory = new CommandFactory(), planet = new Planet(10, 10)) {
        return new Rover(position, facedDirection, commandFactory, planet)
    }
}
