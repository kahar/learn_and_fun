package io.github.kahar.grid.object.action;

import io.github.kahar.grid.direction.Direction;
import lombok.Getter;

@Getter
public class ActionWithDirection extends Action {
    private final Direction direction;

    public ActionWithDirection(ActionType actionType, Direction direction) {
        super(actionType);
        this.direction = direction;
    }
}
