package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

/**
 * Статический объект на карте.
 */
public class StaticGameObject extends BaseGameObject {

    private final StaticGameObjectType staticGameObjectType;

    public StaticGameObject(double x,
                            double y,
                            StaticGameObjectType staticGameObjectType) {
        super(x, y, Units.BUILDING_SIZE, Units.BUILDING_DEFAULT_VISIBILITY_RADIUS, Units.BUILDING_DEFAULT_ACTION_RADIUS,
               0, new Vector(),
                GameObjectType.STATIC);
        this.staticGameObjectType = staticGameObjectType;
    }

    public StaticGameObjectType getStaticGameObjectType() {
        return staticGameObjectType;
    }
}
