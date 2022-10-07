package io.github.kahar.ai.survivors.grid;

import io.github.kahar.ai.survivors.grid.direction.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Vector {
    private final int x;
    private final int y;

    public Vector plus(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector plus(Direction direction) {
        Vector other = direction.getVector();
        return new Vector(this.x + other.x, this.y + other.y);
    }
}
