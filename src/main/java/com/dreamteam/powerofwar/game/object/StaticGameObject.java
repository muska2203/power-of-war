package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.phisics.Vector;

public class StaticGameObject extends BaseGameObject {
    public StaticGameObject(double x, double y) {
        super(x, y, new Vector(), 0);
    }
}
