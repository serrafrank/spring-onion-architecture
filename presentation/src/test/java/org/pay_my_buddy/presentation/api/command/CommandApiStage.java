package org.pay_my_buddy.presentation.api.command;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import lombok.Getter;
import org.pay_my_buddy.entity.commun.api.command.Command;
import org.pay_my_buddy.entity.commun.api.command.CommandHandler;
import org.pay_my_buddy.presentation.api.DefaultCommandApi;
import org.pay_my_buddy.presentation.api.providers.CommandHandlerProvider;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
public class CommandApiStage extends Stage<CommandApiStage> {

    private final List<CommandHandler<?>> commandHandlers = new ArrayList<>();

    private DefaultCommandApi defaultCommandBus;

    private FirstCommandHandlerImpl firstCommandHandler = new FirstCommandHandlerImpl();
    private SecondCommandHandlerImpl secondCommandHandler = new SecondCommandHandlerImpl();


    private Command command = null;

    private Exception exception = null;

    @BeforeStage
    public void setUp() {
        commandHandlers.clear();
        resetBean();

        firstCommandHandler = new FirstCommandHandlerImpl();
        secondCommandHandler = new SecondCommandHandlerImpl();
        command = null;
        exception = null;
    }

    public CommandApiStage a_command() {
        this.command = new TestCommand();
        return self();
    }


    public CommandApiStage a_command_handler_for_the_command() {
        commandHandlers.add(firstCommandHandler);
        resetBean();
        return self();
    }

    public CommandApiStage two_commands_handler_for_the_same_command() {
        commandHandlers.addAll(List.of(firstCommandHandler, secondCommandHandler));
        resetBean();
        return self();
    }


    public CommandApiStage no_handler() {
        return self();
    }

    public CommandApiStage execute_the_command() {
        try {
            defaultCommandBus.execute(command);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public CommandApiStage the_command_is_executed() {
        assertThat(exception).isNull();
        assertThat(firstCommandHandler.isExecuted()).isTrue();
        return self();
    }

    private void resetBean() {
        try {
            final CommandHandlerProvider commandHandlerProvider = new CommandHandlerProvider(commandHandlers);
            defaultCommandBus = new DefaultCommandApi(commandHandlerProvider);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    public void an_exception_is_thrown() {
        assertThat(exception).isNotNull();
    }

    private static class TestCommand implements Command {
    }


    @Getter
    private static class FirstCommandHandlerImpl implements CommandHandler<TestCommand> {

        private boolean executed = false;

        @Override
        public void handle(TestCommand command) {
            executed = true;
        }

        public void reset() {
            executed = false;
        }
    }

    @Getter
    private static class SecondCommandHandlerImpl implements CommandHandler<TestCommand> {

        private boolean executed = false;

        @Override
        public void handle(TestCommand command) {
            executed = true;
        }

        public void reset() {
            executed = false;
        }
    }


}
