package com.stonal.rover.command

import com.stonal.rover.Rover
import com.stonal.rover.command.exception.CannotExecuteCommandException
import com.stonal.rover.exception.ObstacleEncounteredException
import spock.lang.Specification

import java.awt.Point

class MoveForwardCommandTest extends Specification {
    def "Execute"() {
        given:
        def command = new MoveForwardCommand()

        and:
        def rover = Mock(Rover)

        when:
        command.execute(rover)

        then:
        1 * rover.moveForward()
    }

    def "When trying to move forward and there is an obstacle then an exception is thrown"() {
        given:
        def command = new MoveForwardCommand()

        and:
        def rover = Mock(Rover)
        rover.checkForObstacleForward() >> {
            throw new ObstacleEncounteredException(new Point())
        }

        when:
        command.execute(rover)

        then:
        thrown(CannotExecuteCommandException)
    }
}
