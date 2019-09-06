package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.player.Player;

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
    SUICIDE_FACTORY(SuicideFactory::new),

    /**
     * Золотодобытчик
     */
    GOLD_MINER(GoldMiner::new),

    GOLD(GoldResource::new),

    BASE((x, y, player) -> player.getObjectFactory().createBase(x, y));

    private GameObjectCreator objectCreator;

    GameObjectType(GameObjectCreator gameObjectCreator) {
        this.objectCreator = gameObjectCreator;
    }

    @Override
    public GameObject create(double x, double y, Player player) {
        return objectCreator.create(x, y, player);
    }
}