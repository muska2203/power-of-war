package com.dreamteam.powerofwar.game.object;

public enum GameObjectType implements GameObjectCreator {

    /**
     * Любитель обнимашек.
     */
    SUICIDE(SuicideObject::new),

    /**
     * Бегун.
     */
    COWARD(CowardMinion::new);

    private GameObjectCreator objectCreator;

    GameObjectType(GameObjectCreator gameObjectCreator) {
        this.objectCreator = gameObjectCreator;
    }

    @Override
    public GameObject create(double x, double y) {
        return objectCreator.create(x, y);
    }
}