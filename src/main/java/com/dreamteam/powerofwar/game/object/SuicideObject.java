package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.Random;

//TODO: JavaDocs
public class SuicideObject extends BaseGameObject {

    private GameObject target;

    SuicideObject(double x, double y, Player player) {
        super(x, y, Vector.byDirection(1, new Random().nextDouble() * 360), player);
    }

    @Override
    protected double getSpeed() {
        return 4;
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
    public UnitType getType() {
        return UnitType.SUICIDE;
    }

    @Override
    public void update(Board board) {
        if (target != null && (target.isDead() || !GameObjectUtils.checkVisibility(this, target))) {
            target = null;
        }

        this.target = GameObjectUtils.getNearestObject(this,
                board.getGameObjects(),
                gameObject -> gameObject != this && !gameObject.isDead() && gameObject.getOwner() != this.getOwner()
                        && GameObjectUtils.checkVisibility(this, gameObject));
        if (target != null) {
            Vector vector = new Vector(target.getX() - this.getX(), target.getY() - this.getY());
            this.setSpeedVector(vector);
        }
    }

    @Override
    public String toString() {
        return "SuicideObject{id: " + this.getId() + "}";
    }
}
