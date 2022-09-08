package io.github.kahar.grid;

import io.github.kahar.grid.direction.Direction;
import io.github.kahar.grid.object.*;
import io.github.kahar.grid.object.action.Action;
import io.github.kahar.grid.object.action.ActionType;
import io.github.kahar.grid.object.action.ActionWithDirection;
import io.github.kahar.grid.object.view.View;

import java.util.*;

public class Grid {

    public static final String EMPTY_FIELD = " ";
    private final List<GridObject> space;
    private final int width;
    private final int height;
    private final Map<String, String> legend = new HashMap<String, String>() {{
        put("#", "Wall");
        put("O", "Critter");
        put("*", "Plant");
        put("@", "Tiger");
    }};
    private long turnCounter = 0;
    private long lastRoundWhenEverythingLives = 0;

    public Grid(List<String> map) {
        this.width = map.size();
        this.height = map.get(0).length();
        this.space = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            this.space.add(null);
        }
        for (int line = 0; line < width; line++) {
            for (int column = 0; column < height; column++) {
                set(new Vector(line, column), elementFromChar(map.get(line).charAt(column)));
            }
        }
    }

    private GridObject elementFromChar(char c) {
        String gridObjectClass = this.legend.get(String.valueOf(c));
        if (gridObjectClass == null) {
            return null;
        }
        switch (gridObjectClass) {
            case "Wall":
                return new Wall();
            case "Plant":
                return new Plant();
            case "Critter":
                return new PlantEater();
            case "Tiger":
                return new Tiger();
            default:
                return null;
        }
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

    public GridObject get(Vector vector) {
        return this.space.get(vector.getX() + this.width * vector.getY());
    }

    public void set(Vector vector, GridObject gridObject) {
        this.space.set(vector.getX() + this.width * vector.getY(), gridObject);
    }

    public void turn() {
        turnCounter++;
        Set<GridObject> acted = new HashSet<>();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Vector vector = new Vector(x, y);
                GridObject element = get(vector);
                if (acted.contains(element) || element == null || !element.canAct()) {
                    continue;
                }
                acted.add(element);
                acted.add(letAct(element, vector));
            }
        }
    }

    private GridObject letAct(GridObject element, Vector vector) {
        if (element.getEnergy() <= 1) {
            die(vector);
            return element;
        }
        Action action = element.act(new View(this, vector));
        if (action == null) {
            live(vector);
            return element;
        }
        ActionType actionType = action.getActionType();
        if (action instanceof ActionWithDirection) {
            ActionWithDirection directedAction = (ActionWithDirection) action;
            if (directedAction.getDirection() != null) {
                switch (actionType) {
                    case REPRODUCE:
                        return reproduce(element, directedAction, vector);
                    case EAT:
                        eat(element, directedAction, vector);
                        return element;
                    case MOVE:
                        move(element, directedAction, vector);
                        return element;
                }
            }

        } else {
            if (actionType == ActionType.GROW) {
                grow(element);
            }
        }
        return element;
    }

    private void live(Vector vector) {
        GridObject element = this.get(vector);
        if (element != null && element.getEnergy() != null && element.getEnergy() > 0 && !element.getChar().equals("*")) {
            element.setEnergy(element.getEnergy() - 5);
        }
    }

    private void die(Vector vector) {
        this.set(vector, null);
    }

    private void move(GridObject element, ActionWithDirection directedAction, Vector vector) {
        Vector dest = checkDestination(directedAction, vector);
        if (this.get(dest) == null) {
            this.set(vector, null);
            this.set(dest, element);
        }
        element.setEnergy(element.getEnergy() - 10);
    }

    private GridObject reproduce(GridObject element, ActionWithDirection directedAction, Vector vector) {
        Vector dest = checkDestination(directedAction, vector);
        if (this.get(dest) == null) {
            GridObject reproduced = element.reproduce();
            this.set(dest, reproduced);
            return reproduced;
        }
        return null;
    }

    private void eat(GridObject element, ActionWithDirection directedAction, Vector vector) {
        Vector dest = checkDestination(directedAction, vector);
        if (this.get(dest) != null) {
            GridObject aim = this.get(dest);
            element.setEnergy(element.getEnergy() + aim.getEnergy());
            this.set(dest, null);
        }
    }


    private void grow(GridObject element) {
        element.setEnergy(element.getEnergy() + 5);
    }

    private Vector checkDestination(Action action, Vector vector) {
        if (action instanceof ActionWithDirection) {
            ActionWithDirection directedAction = (ActionWithDirection) action;
            if (directedAction.getDirection() != null) {
                Direction direction = directedAction.getDirection();
                Vector dest = vector.plus(direction.getVector());
                if (isInside(dest))
                    return dest;
            }
        }
        return null;
    }

    public Map<String, Integer> generateStatistics() {
        Map<String, Integer> result = new HashMap<>();
        result.put("Plant", 0);
        result.put("PlantEater", 0);
        result.put("Tiger", 0);
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Vector vector = new Vector(x, y);
                GridObject element = get(vector);
                if (element instanceof Plant) {
                    result.put("Plant", result.get("Plant") + 1);
                } else if (element instanceof PlantEater) {
                    result.put("PlantEater", result.get("PlantEater") + 1);
                } else if (element instanceof Tiger) {
                    result.put("Tiger", result.get("Tiger") + 1);
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                GridObject element = get(new Vector(x, y));
                output.append(charFromElement(element));
            }
            output.append("\n\n");
        }

        Map<String, Integer> statistics = generateStatistics();
        if (statistics.get("Plant") > 0 && statistics.get("PlantEater") > 0 && statistics.get("Tiger") > 0) {
            lastRoundWhenEverythingLives = turnCounter;
        }


        output.append("\n world turn: ").append(turnCounter).append("\n lastRoundWhenEverythingLives: ").append(lastRoundWhenEverythingLives).append("\n Plants: ").append(statistics.get("Plant")).append("\n PlantEaters: ").append(statistics.get("PlantEater")).append("\n Tigers: ").append(statistics.get("Tiger")).append("\n");
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
