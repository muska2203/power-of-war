package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;

public enum GameObjectType implements GameObjectCreator {

    /**
     * Любитель обнимашек.
     */
    SUICIDE(SuicideObject::new),

    /**
     * Бегун.
     */
    COWARD(CowardMinion::new),

    /**
     * Фабрика любителей обнимашек.
     */
    SUICIDE_FACTORY(SuicideFactory::new);

    private GameObjectCreator objectCreator;

    GameObjectType(GameObjectCreator gameObjectCreator) {
        this.objectCreator = gameObjectCreator;
    }

    @Override
    public GameObject create(double x, double y, User user) {
        return objectCreator.create(x, y, user);
    }
}