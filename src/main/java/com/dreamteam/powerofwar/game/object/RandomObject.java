package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.phisics.Vector;

import java.util.Random;

public class RandomObject extends BaseGameObject {
    public RandomObject(double x, double y) {
        super(x, y, Vector.byDirection(1, new Random().nextDouble() * 360), new Random().nextInt(4) + 1);
    }
}
