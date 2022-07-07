package io.github.kahar.grid.object.view;

import io.github.kahar.grid.Grid;
import io.github.kahar.grid.Vector;
import io.github.kahar.grid.direction.Direction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class View {
    private final Grid grid;
    private final Vector vector;

    public Direction find(String ch) {
        return grid.find(ch, vector);
    }

    public int count(String ch) {
        return grid.findAll(ch, vector).size();
    }
}
