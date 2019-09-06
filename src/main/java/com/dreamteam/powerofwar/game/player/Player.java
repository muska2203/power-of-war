package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.object.GameObjectFactory;

public class Player {

    public static final Player NEUTRAL_PLAYER = new Player("Neutral Player");

    private String name;

    private PlayerContext playerContext;

    private GameObjectFactory gameObjectFactory;

    public Player(String name) {
        this.name = name;
        this.playerContext = new PlayerContext();
        this.gameObjectFactory = new GameObjectFactory(this);
    }

    public String getName() {
        return this.name;
    }

    public boolean isEnemy(Player player) {
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
