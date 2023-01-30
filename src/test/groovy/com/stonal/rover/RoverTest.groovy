package com.stonal.rover

import spock.lang.Specification
import spock.lang.Unroll

import java.awt.*

@Unroll
class RoverTest extends Specification {
    def "A Rover can be initialized with a starting point and the direction it is facing"() {
        when:
        def rover = new Rover(new Point(x, y), facedDirection)

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
        new Rover(point, facedDirection)

        then:
        def e = thrown(FailedToInitializeRoverException)
        e.message == exceptionMessage

        where:
        point       | facedDirection          | exceptionMessage
        null        | CardinalDirection.NORTH | "The initial position provided cannot be null"
        new Point() | null                    | "The faced direction provided cannot be null"
        null        | null                    | "The initial position provided cannot be null"
    }
}
