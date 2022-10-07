package io.github.kahar.ai.survivors.grid.object.view;

import io.github.kahar.ai.survivors.grid.Grid;
import io.github.kahar.ai.survivors.grid.Vector;
import io.github.kahar.ai.survivors.grid.direction.Direction;
import io.github.kahar.ai.survivors.grid.object.GridObject;
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

    public float[] toFloatArrayFreeSpace() {
        float[] result = new float[8];
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[i];
            Vector destination = vector.plus(direction);
            if (!grid.isInside(destination)) {
                result[i] = (float) 1.0;
                continue;
            }
            GridObject freeSpace = grid.get(destination);
            if (freeSpace == null) {
                result[i] = (float) 1.0;
            } else {
                result[i] = (float) 0.0;
            }
        }
        return result;
    }
}
