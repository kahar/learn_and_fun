package io.github.kahar.ai.survivors.grid.object.action;

import io.github.kahar.ai.survivors.grid.direction.Direction;
import lombok.Getter;

@Getter
public class ActionWithDirection extends Action {
    private final Direction direction;

    public ActionWithDirection(ActionType actionType, Direction direction) {
        super(actionType);
        this.direction = direction;
    }
}
