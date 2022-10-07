package io.github.kahar.ai.survivors.grid;


import io.github.kahar.ai.survivors.grid.direction.Direction;
import io.github.kahar.ai.survivors.grid.object.Creature;
import io.github.kahar.ai.survivors.grid.object.GridObject;
import io.github.kahar.ai.survivors.grid.object.action.Action;
import io.github.kahar.ai.survivors.grid.object.action.ActionType;
import io.github.kahar.ai.survivors.grid.object.action.ActionWithDirection;
import io.github.kahar.ai.survivors.grid.object.view.View;

import java.util.*;

public class Grid {

    public static final String EMPTY_FIELD = " ";
    private final List<Creature> space;
    private final int width;
    private final int height;
    private final Map<String, String> legend = new HashMap<>() {{
        put("X", "Creature");
    }};
    private final long lastRoundWhenEverythingLives = 0;
    private long turnCounter = 0;

    public Grid(int width, int height, List<Creature> numberOfCreatures) {
        this.width = width;
        this.height = height;
        this.space = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            this.space.add(null);
        }
        numberOfCreatures.forEach(creature -> {
            tryPutSomewhere(width, height, creature);
        });
    }

    private void tryPutSomewhere(int width, int height, Creature creature) {
        Random rand = new Random();
        int randWidth = rand.nextInt(width);
        int randHeight = rand.nextInt(height);
        if (get(new Vector(randWidth, randHeight)) == null) {
            set(new Vector(randWidth, randHeight), creature);
            return;
        }
        tryPutSomewhere(width, height, creature);
    }

    private String charFromElement(GridObject element) {
        if (element == null)
            return EMPTY_FIELD;
        else
            return element.getChar();
    }

    public boolean isInside(Vector vector) {
        return vector.getX() >= 0 && vector.getX() < this.width &&
                vector.getY() >= 0 && vector.getY() < this.height;
    }

    public Creature get(Vector vector) {
        return this.space.get(vector.getX() + this.width * vector.getY());
    }

    public void set(Vector vector, Creature creature) {
        this.space.set(vector.getX() + this.width * vector.getY(), creature);
    }

    public void turn() {
        turnCounter++;
        Set<GridObject> acted = new HashSet<>();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Vector vector = new Vector(x, y);
                Creature creature = get(vector);
                if (acted.contains(creature) || creature == null || !creature.canAct()) {
                    continue;
                }
                acted.add(creature);
                acted.add(letAct(creature, vector));
            }
        }
    }

    private GridObject letAct(Creature creature, Vector vector) {
        Action action = creature.act(new View(this, vector));
        if (action == null) {
            return creature;
        }
        ActionType actionType = action.getActionType();
        if (action instanceof ActionWithDirection directedAction) {
            if (directedAction.getDirection() != null) {
                if (actionType == ActionType.MOVE) {
                    move(creature, directedAction, vector);
                    return creature;
                }
            }
        }
        return creature;
    }

    private void move(Creature creature, ActionWithDirection directedAction, Vector vector) {
        Vector dest = checkDestination(directedAction, vector);
        if (dest != null && this.get(dest) == null) {
            this.set(vector, null);
            this.set(dest, creature);
        }
//        element.setEnergy(element.getEnergy() - 10);
    }

    private Vector checkDestination(Action action, Vector vector) {
        if (action instanceof ActionWithDirection directedAction) {
            if (directedAction.getDirection() != null) {
                Direction direction = directedAction.getDirection();
                Vector dest = vector.plus(direction.getVector());
                if (isInside(dest))
                    return dest;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("  0123456789\n");
        for (int x = 0; x < width; x++) {
            output.append(x + " ");
            for (int y = 0; y < height; y++) {
                GridObject element = get(new Vector(x, y));
                output.append(charFromElement(element));
            }
            output.append("\n");
        }

        output.append("\n world turn: ").append(turnCounter).append("\n lastRoundWhenEverythingLives: ").append(lastRoundWhenEverythingLives);
        return output.toString();
    }

    public Direction find(String ch, Vector v) {
        List<Direction> found = findAll(ch, v);
        if (found.size() == 0) {
            return null;
        }
        return randomElement(found);
    }

    public List<Direction> findAll(String ch, Vector v) {
        List<Direction> found = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            GridObject element = get(v.plus(dir.getVector()));
            if (element != null && ch != null) {
                if (ch.equals(element.getChar())) {
                    found.add(dir);
                }
            } else if (element == null && ch == null) {
                found.add(dir);
            }
        }
        return found;
    }

    private Direction randomElement(List<Direction> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
