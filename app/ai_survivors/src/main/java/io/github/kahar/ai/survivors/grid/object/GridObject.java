package io.github.kahar.ai.survivors.grid.object;

import io.github.kahar.ai.survivors.grid.object.action.Action;
import io.github.kahar.ai.survivors.grid.object.view.View;

public interface GridObject {
    String getChar();

    boolean canAct();

    Action act(View view);

    GridObject reproduce();
}
