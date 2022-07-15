package io.github.kahar.grid.object;

import io.github.kahar.grid.object.action.Action;
import io.github.kahar.grid.object.view.View;

public class Wall implements GridObject {
    @Override
    public String getChar() {
        return "#";
    }

    @Override
    public boolean canAct() {
        return false;
    }

    @Override
    public Action act(View view) {
        return null;
    }

    @Override
    public Integer getEnergy() {
        return null;
    }

    @Override
    public void setEnergy(Integer energy) {

    }

    @Override
    public GridObject reproduce() {
        return null;
    }
}
