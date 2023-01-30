package com.stonal.rover.command

import com.stonal.rover.command.exception.UnknownCommandException
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CommandFactoryTest extends Specification {
    def "charToCommand(#character) should return a #expectedCommand.simpleName"() {
        given:
        def commandFactory = new CommandFactory()

        expect:
        commandFactory.charToCommand(character).getClass() == expectedCommand

        where:
        character   | expectedCommand
        'f' as char | MoveForwardCommand
        'b' as char | MoveBackwardCommand
        'l' as char | RotateLeftCommand
        'r' as char | RotateRightCommand
    }

    def "When an invalid character is sent to charToCommand then it should throw an exception"() {
        given:
        def commandFactory = new CommandFactory()

        when:
        commandFactory.charToCommand('a' as char)

        then:
        thrown(UnknownCommandException)
    }
}
