package com.dreamteam.powerofwar.common.player;

import java.util.Objects;

import com.dreamteam.powerofwar.common.types.ResourceType;

/**
 * TODO: JavaDoc
 */
public class Player {

    private static int ID_GENERATOR = 0;

    private final int id;

    private final String name;

    private final PlayerContext playerContext;

    public Player(String name) {
        this.id = ++ID_GENERATOR;
        this.name = name;
        this.playerContext = new PlayerContext();
        this.playerContext.addResource(ResourceType.GOLD, 20);
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
