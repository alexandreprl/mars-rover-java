package com.stonal.rover.command

import com.stonal.rover.Rover
import spock.lang.Specification

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
}
