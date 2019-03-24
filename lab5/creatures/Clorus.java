package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }
    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    //If a Clorus attacks another creature,
    //it should gain that creature’s energy.
    public void attack(Creature c) {
        energy = c.energy();
    }

    @Override
    public Clorus replicate() {
        this.energy /= 2;
        Clorus offspring =  new Clorus(energy);
        return offspring;
    }
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.addLast(d);
            } else if (neighbors.get(d).name().equals("plips")) {
                plipNeighbors.addLast(d);
            }
        }
        //Rule 1
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        //Rule 2
        if (plipNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }
        //Rule 3
        if (this.energy > 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        //Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }

}
