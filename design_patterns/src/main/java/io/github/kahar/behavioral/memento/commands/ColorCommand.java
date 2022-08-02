package io.github.kahar.behavioral.memento.commands;

import io.github.kahar.behavioral.memento.editor.Editor;
import io.github.kahar.refactoring_guru.shapes.Shape;

import java.awt.*;


public class ColorCommand implements Command {
    private Editor editor;
    private Color color;

    public ColorCommand(Editor editor, Color color) {
        this.editor = editor;
        this.color = color;
    }

    @Override
    public String getName() {
        return "Colorize: " + color.toString();
    }

    @Override
    public void execute() {
        for (Shape child : editor.getShapes().getSelected()) {
            child.setColor(color);
        }
    }
}