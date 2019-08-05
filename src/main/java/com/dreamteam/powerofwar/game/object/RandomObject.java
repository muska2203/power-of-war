package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.Random;

public class RandomObject extends BaseGameObject {
    public RandomObject(double x, double y) {
        super(x, y, Units.MINION_SIZE,
                Units.MINION_DEFAULT_VISIBILITY_RADIUS,
                Units.MINION_DEFAULT_ACTION_RADIUS,
                new Random().nextDouble() * 4 + 1,
                Vector.byDirection(1, new Random().nextDouble() * 360),
                GameObjectType.MINION);
    }
}
