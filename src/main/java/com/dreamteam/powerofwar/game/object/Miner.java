package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.StoreResourcesAction;
import com.dreamteam.powerofwar.game.action.MineResourceAction;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: JavaDoc
 */
public abstract class Miner extends BaseGameObject {

    protected int value = 0;
    protected GameObject target;

    Miner(double x, double y, Player player) {
        super(x, y, new Vector(), player);
    }

    public int getValue() {
        return value;
    }

    public void addResource(int value) {
        this.value += value;
    }

    @Override
    public String toString() {
        return "Miner[ " + this.getValue() + " / " + this.getCapacity() + " ]";
    }

    public void resetValue() {
        this.value = 0;
    }

    @Override
    public void update(Board board) {
        if (target != null) {
            // если дошли до цели
            if (GameObjectUtils.checkCollision(this, target)) {
                if (BuildingType.BASE.equals(target.getType())) {
                    board.addAction(new StoreResourcesAction(this));
                } else {
                    board.addAction(new MineResourceAction((Resource) target, this));
                }
                this.target = null;
            }
        } else {
            this.target = chooseTarget(board);
        }

        if (this.target != null) {
            this.setSpeedVector(Vector.byTarget(this, target));
        } else {
            this.setSpeedVector(new Vector());
        }
    }

    private GameObject chooseTarget(Board board) {
        if (this.isFull()) {
            List<GameObject> bases = board.getGameObjects()
                    .stream()
                    .filter(gameObject -> BuildingType.BASE.equals(gameObject.getType()) && !gameObject.getOwner().isEnemyFor(this.getOwner()))
                    .collect(Collectors.toList());
            return GameObjectUtils.getNearestObject(this, bases);
        } else {
            List<GameObject> mines = board.getGameObjects()
                    .stream()
                    .filter(gameObject -> getResourceType().equals(gameObject.getType()))
                    .collect(Collectors.toList());
            return GameObjectUtils.getNearestObject(this, mines);
        }
    }

    public abstract ResourceType getResourceType();

    public abstract int getCapacity();

    public boolean isFull() {
        return value >= getCapacity();
    }
}
