package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.user.User;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class GoldMiner extends Miner {

    GoldMiner(double x, double y, User user) {
        super(x, y, user);
    }

    @Override
    public GameObjectType getResource() {
        return GameObjectType.GOLD;
    }

    @Override
    public int getCapacity() {
        return 7;
    }

    @Override
    protected double getSpeed() {
        return 6;
    }

    @Override
    public double getSize() {
        return Units.MINION_SIZE;
    }

    @Override
    public double getVisibilityRadius() {
        return Units.MINION_DEFAULT_VISIBILITY_RADIUS;
    }

    @Override
    public double getActionRadius() {
        return Units.MINION_DEFAULT_ACTION_RADIUS;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.GOLD_MINER;
    }

    @Override
    public void update(Board board) {
        if (target != null) {
            // если дошли до цели
            if (GameObjectUtils.checkCollision(this, target)) {
                if (GameObjectType.BASE.equals(target.getType())) {
                    board.addAction(new BringResourceAction((Base) target, this));
                } else {
                    board.addAction(new MineResourceAction((Resource) target, this));
                }
                this.target = null;
            }
            return;
        }

        // ищем новую цели для рабочего
        List<GameObject> mines = board.getGameObjects()
                    .stream()
                    .filter(gameObject -> GameObjectType.GOLD.equals(gameObject.getType()))
                    .collect(Collectors.toList());


        List<GameObject> bases = board.getGameObjects()
                .stream()
                .filter(gameObject -> GameObjectType.BASE.equals(gameObject.getType()))
                .collect(Collectors.toList());

        if (this.value < this.getCapacity() && !mines.isEmpty()) {
            this.target = GameObjectUtils.getNearestObject(this, mines);
        } else if (this.value > 0 && mines.isEmpty() && !bases.isEmpty()) {
            this.target = GameObjectUtils.getNearestObject(this, bases);
        } else if (this.value == this.getCapacity() && !bases.isEmpty()) {
            this.target = GameObjectUtils.getNearestObject(this, bases);
        } else {
            target = null;
            this.setSpeedVector(new Vector());
        }
        if (this.target != null) {
            this.setSpeedVector(Vector.byTarget(this, target));
        }
    }
}
