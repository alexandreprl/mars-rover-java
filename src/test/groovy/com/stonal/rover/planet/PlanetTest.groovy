package com.stonal.rover.planet

import com.stonal.rover.CardinalDirection
import com.stonal.rover.planet.exception.InvalidPlanetSizeException
import com.stonal.rover.planet.exception.InvalidPositionOnPlanetException
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

    def "When trying to add an obstacle to a valid position then no exception should be thrown"() {
        given:
        def planet = new Planet(10, 10)

        when:
        planet.addObstacle(new Point(x, y))

        then:
        noExceptionThrown()

        where:
        x | y
        0 | 0
        9 | 9
    }

    def "When trying to add an obstacle to an invalid position then an exception must be thrown"() {
        given:
        def planet = new Planet(10, 10)

        when:
        planet.addObstacle(new Point(x, y))

        then:
        thrown(InvalidPositionOnPlanetException)

        where:
        x  | y
        -1 | 0
        0  | -1
        10 | 0
        0  | 10
    }

    def "When checking for obstacle on a valid position then no exception should be thrown"() {
        given:
        def planet = new Planet(10, 10)

        when:
        planet.hasObstacleInPosition(new Point(x, y))

        then:
        noExceptionThrown()

        where:
        x | y
        0 | 0
        9 | 9
    }

    def "When checking for obstacle on an invalid position then an exception must be thrown"() {
        given:
        def planet = new Planet(10, 10)

        when:
        planet.hasObstacleInPosition(new Point(x, y))

        then:
        thrown(InvalidPositionOnPlanetException)

        where:
        x  | y
        -1 | 0
        0  | -1
        10 | 0
        0  | 10
    }

    def "Given an obstacle is on #obstaclePosition.x,#obstaclePosition.y then hasObstacleInPosition for #verificationPosition.x,#verificationPosition.y is #expected"() {
        given:
        def planet = new Planet(10, 10)
        planet.addObstacle(obstaclePosition)

        expect:
        planet.hasObstacleInPosition(verificationPosition) == expected

        where:
        obstaclePosition | verificationPosition | expected
        new Point(0, 0)  | new Point(0, 0)      | true
        new Point(0, 0)  | new Point(1, 1)      | false
    }
}
