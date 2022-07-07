package io.github.kahar.grid.object;

import io.github.kahar.grid.object.action.Action;
import io.github.kahar.grid.object.view.View;

public interface GridObject {
    String getChar();


    boolean canAct();

    Action act(View view);

    Integer getEnergy();

    void setEnergy(Integer energy);


    GridObject reproduce();
}
