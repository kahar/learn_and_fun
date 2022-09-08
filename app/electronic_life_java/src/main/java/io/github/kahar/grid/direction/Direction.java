package io.github.kahar.grid.direction;

import io.github.kahar.grid.Vector;
import lombok.Getter;

@Getter
public enum Direction {
    N(new Vector(1, 0)),
    NE(new Vector(1, 1)),
    E(new Vector(0, 1)),
    SE(new Vector(-1, 1)),
    S(new Vector(-1, 0)),
    SW(new Vector(-1, -1)),
    W(new Vector(0, -1)),
    NW(new Vector(1, -1));

    private final Vector vector;

    Direction(Vector v) {
        this.vector = v;
    }

}
