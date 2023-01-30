package com.stonal.rover.planet

import com.stonal.rover.CardinalDirection
import com.stonal.rover.planet.exception.InvalidPlanetSizeException
import spock.lang.Specification
import spock.lang.Unroll

import java.awt.*

@Unroll
class PlanetTest extends Specification {
    def "A planet can be initialized with a valid size"() {
        when:
        def planet = new Planet(width, height)

        then:
        planet.width == width
        planet.height == height

        where:
        width | height
        1     | 1
        1     | 2
        2     | 1
    }

    def "When a planet is initialized with an invalid size, then an exception is thrown"() {
        when:
        new Planet(width, height)

        then:
        thrown(InvalidPlanetSizeException)

        where:
        width | height
        0     | 0
        0     | 1
        1     | 0
        -1    | -1
        0     | -1
        -1    | 0
    }

    def "nextPositionInDirection"() {
        given:
        def planet = new Planet(10, 10)

        expect:
        planet.nextPositionInDirection(startingPosition, direction) == expectedPosition

        where:
        startingPosition | direction               | expectedPosition
        new Point(0, 0)  | CardinalDirection.NORTH | new Point(0, 1)
        new Point(0, 0)  | CardinalDirection.SOUTH | new Point(0, 9)
        new Point(0, 0)  | CardinalDirection.WEST  | new Point(9, 0)
        new Point(0, 0)  | CardinalDirection.EAST  | new Point(1, 0)

        new Point(9, 9)  | CardinalDirection.NORTH | new Point(9, 0)
        new Point(9, 9)  | CardinalDirection.SOUTH | new Point(9, 8)
        new Point(9, 9)  | CardinalDirection.WEST  | new Point(8, 9)
        new Point(9, 9)  | CardinalDirection.EAST  | new Point(0, 9)
    }
}
