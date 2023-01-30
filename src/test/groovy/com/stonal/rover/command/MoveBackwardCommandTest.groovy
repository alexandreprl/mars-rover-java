package com.stonal.rover.command

import com.stonal.rover.Rover
import spock.lang.Specification

class MoveBackwardCommandTest extends Specification {
    def "Execute"() {
        given:
        def command = new MoveBackwardCommand()

        and:
        def rover = Mock(Rover)

        when:
        command.execute(rover)

        then:
        1 * rover.moveBackward()
    }
}
