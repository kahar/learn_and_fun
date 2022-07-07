package io.github.kahar.grid;

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
}
