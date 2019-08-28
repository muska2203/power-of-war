package com.dreamteam.powerofwar.game.user;

public class User {

    public static final User NEUTRAL_USER = new User("Neutral User");

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
