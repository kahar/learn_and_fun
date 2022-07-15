package io.github.kahar;

import io.github.kahar.grid.Grid;

import java.util.List;

public class World {
    private final Grid grid;

    public World(List<String> map) {
        this.grid = new Grid(map);
    }

    public void turn() {
        this.grid.turn();
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
