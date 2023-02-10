package com.stonal.rover

import com.stonal.rover.command.Command
import com.stonal.rover.command.CommandFactory
import com.stonal.rover.command.MoveForwardCommand
import com.stonal.rover.command.exception.CannotExecuteCommandException
import com.stonal.rover.exception.CannotCheckForObstacleException
import com.stonal.rover.exception.FailedToInitializeRoverException
import com.stonal.rover.exception.ObstacleEncounteredException
import com.stonal.rover.planet.Planet
import com.stonal.rover.planet.exception.InvalidPositionOnPlanetException
import spock.lang.Specification
import spock.lang.Unroll

import java.awt.*

@Unroll
class RoverTest extends Specification {
    def "A Rover can be initialized with a starting point and the direction it is facing"() {
        when:
        def rover = createRover(new Point(x, y), facedDirection)

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
        createRover(point, facedDirection)

        then:
        def e = thrown(FailedToInitializeRoverException)
        e.message == exceptionMessage

        where:
        point       | facedDirection          | commandFactory       | exceptionMessage
        null        | CardinalDirection.NORTH | new CommandFactory() | "The initial position provided cannot be null"
        new Point() | null                    | new CommandFactory() | "The faced direction provided cannot be null"
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

    def "When checking for obstacle backward and there is an obstacle then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> true

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleBackward()

        then:
        thrown(ObstacleEncounteredException)
    }

    def "When checking for obstacle forward and there is an obstacle then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> true

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleForward()

        then:
        thrown(ObstacleEncounteredException)
    }

    def "When checking for obstacle backward and there is no obstacle then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> false

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleBackward()

        then:
        notThrown(ObstacleEncounteredException)
    }

    def "When checking for obstacle forward and there is no obstacle then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> false

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleForward()

        then:
        notThrown(ObstacleEncounteredException)
    }

    def "When checking for obstacle forward and there is no obstacle then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> false

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleForward()

        then:
        notThrown(ObstacleEncounteredException)
    }

    def "When checking for obstacle forward and the position forward is invalid then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> { throw new InvalidPositionOnPlanetException(Mock(Point)) }

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleForward()

        then:
        def e = thrown(CannotCheckForObstacleException)
        def cause = e.getCause()
        cause instanceof InvalidPositionOnPlanetException
    }

    def "When checking for obstacle backward and the position backward is invalid then an exception must be thrown"() {
        given:
        def planet = Mock(Planet)
        def obstaclePosition = Mock(Point)
        planet.nextPositionInDirection(_, _) >> obstaclePosition
        planet.hasObstacleInPosition(obstaclePosition) >> { throw new InvalidPositionOnPlanetException(Mock(Point)) }

        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.checkForObstacleBackward()

        then:
        def e = thrown(CannotCheckForObstacleException)
        def cause = e.getCause()
        cause instanceof InvalidPositionOnPlanetException
    }

    def "execute"() {
        given:
        def rover = createRover()

        and:
        def command = Mock(Command)

        when:
        rover.execute(command)

        then:
        1 * command.execute(rover)
    }
    def "When a sequence of commands encounters an obstacle then the rover moves up to the last possible point, aborts the sequence and reports the obstacle"() {
        given:
        def planet = new Planet(10, 10)
        planet.addObstacle(new Point(0, 2))
        def rover = createRover(new Point(0, 0), CardinalDirection.NORTH, planet)

        when:
        rover.execute(new MoveForwardCommand())
        rover.execute(new MoveForwardCommand())
        rover.execute(new MoveForwardCommand())
        rover.execute(new MoveForwardCommand())

        then:
        def e = thrown(CannotExecuteCommandException)
        def cause = e.getCause()
        cause instanceof ObstacleEncounteredException
        cause.message == "Encountered an obstacle in position 0,2"
        rover.position == new Point(0, 1)
    }



    def createRover(position = new Point(0, 0), facedDirection = CardinalDirection.NORTH, planet = new Planet(10, 10)) {
        return new Rover(position, facedDirection, planet)
    }
}
