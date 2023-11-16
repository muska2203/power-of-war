package com.dreamteam.powerofwar.server.game.player;

import java.util.Objects;

import com.dreamteam.powerofwar.game.types.ResourceType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
