package io.github.kahar.grid.object;

import io.github.kahar.grid.direction.Direction;
import io.github.kahar.grid.object.action.Action;
import io.github.kahar.grid.object.action.ActionType;
import io.github.kahar.grid.object.action.ActionWithDirection;
import io.github.kahar.grid.object.view.View;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plant implements GridObject {

    private Integer energy;

    public Plant() {
        this.energy = 100;
    }

    @Override
    public String getChar() {
        return "*";
    }

    @Override
    public boolean canAct() {
        return true;
    }

    @Override
    public Action act(View view) {
        if (this.energy > 150) {
            Direction direction = view.find(null);
            if (direction != null) {
                return new ActionWithDirection(ActionType.REPRODUCE, direction);
            }
        }
        if (this.energy < 200) {
            return new Action(ActionType.GROW);
        }
        return null;
    }

    @Override
    public GridObject reproduce() {
        Plant result = new Plant();
        this.energy = this.energy - result.energy;
        return result;
    }
}
