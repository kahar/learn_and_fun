package io.github.kahar.behavioral.memento.commands;

public interface Command {
    String getName();

    void execute();
}