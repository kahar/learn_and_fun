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
public class Tiger implements GridObject {

    private Integer energy;

    public Tiger() {
        this.energy = 2000;
    }

    @Override
    public String getChar() {
        return "o";
    }

    @Override
    public boolean canAct() {
        return true;
    }

    @Override
    public Action act(View view) {
        Direction freeSpaceDirection = view.find(null);
        Direction mealDirection = view.find("O");
        int mealCount = view.count("O");

        if (this.energy > 6000 && freeSpaceDirection != null) {
            return new ActionWithDirection(ActionType.REPRODUCE, freeSpaceDirection);
        }

        if (mealDirection != null && mealCount > 2) {
            return new ActionWithDirection(ActionType.EAT, mealDirection);
        }
        if (freeSpaceDirection != null) {
            return new ActionWithDirection(ActionType.MOVE, freeSpaceDirection);
        }

        return null;
    }

    @Override
    public GridObject reproduce() {
        Tiger result = new Tiger();
        this.energy = this.energy - result.energy;
        return result;
    }
}
