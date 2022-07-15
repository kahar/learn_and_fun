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
public class PlantEater implements GridObject {

    private Integer energy;

    public PlantEater() {
        this.energy = 200;
    }

    @Override
    public String getChar() {
        return "O";
    }

    @Override
    public boolean canAct() {
        return true;
    }

    @Override
    public Action act(View view) {
        Direction freeSpaceDirection = view.find(null);
        Direction plantDirection = view.find("*");
        int plantCount = view.count("*");

        if (this.energy > 600 && freeSpaceDirection != null) {
            return new ActionWithDirection(ActionType.REPRODUCE, freeSpaceDirection);
        }

        if (plantDirection != null && plantCount > 2) {
            return new ActionWithDirection(ActionType.EAT, plantDirection);
        }
        if (freeSpaceDirection != null) {
            return new ActionWithDirection(ActionType.MOVE, freeSpaceDirection);
        }


        return null;
    }

    @Override
    public GridObject reproduce() {
        PlantEater result = new PlantEater();
        this.energy = this.energy - result.energy;
        return result;
    }
}
