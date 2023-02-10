package com.stonal.rover.command

import com.stonal.rover.Rover
import com.stonal.rover.command.exception.UnknownCommandException
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CommandInterpreterTest extends Specification {
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
        def commandInterpreter = new CommandInterpreter(commandFactory)

        and:
        def rover = Mock(Rover)

        when:
        commandInterpreter.interpret("ab", rover)

        then:
        1 * rover.execute(commandA)
        1 * rover.execute(commandB)
    }

    def "When an unknown command is received then an exception is thrown"() {
        given:
        def commandFactory = Mock(CommandFactory)
        commandFactory.charToCommand(_) >> {
            char c ->
                throw new UnknownCommandException()
        }

        and:
        def rover = Mock(Rover)

        and:
        def commandInterpreter = new CommandInterpreter(commandFactory)

        when:
        commandInterpreter.interpret("ab", rover)

        then:
        thrown(UnknownCommandException)
    }

}
