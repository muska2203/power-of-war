package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.object.type.ResourceType;

/**
 * TODO: JavaDoc
 */
public class Player {

    private static int ID_GENERATOR = 0;

    private int id;

    private String name;

    private PlayerContext playerContext;

    private GameObjectFactory gameObjectFactory;

    public Player(String name) {
        this.id = ++ID_GENERATOR;
        this.name = name;
        this.playerContext = new PlayerContext();
        this.playerContext.addResource(ResourceType.GOLD, 20);
        this.gameObjectFactory = new GameObjectFactory(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnemyFor(Player player) {
        return player != this;
    }

    public PlayerContext getContext() {
        return playerContext;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public GameObjectFactory getObjectFactory() {
        return gameObjectFactory;
    }
}
