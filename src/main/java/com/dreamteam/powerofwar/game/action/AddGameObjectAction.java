package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;
import com.dreamteam.powerofwar.game.player.Player;

public class AddGameObjectAction implements Action {

    private Board board;
    private GameObjectCreator creator;
    private double x;
    private double y;
    private Player owner;

    public AddGameObjectAction(Board board, double x, double y, GameObjectCreator creator, Player owner) {
        this.board = board;
        this.creator = creator;
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    @Override
    public void execute() {
        try {
            board.addGameObject(creator.create(x, y, owner));
        } catch (TooManyObjectsException ignore) {}
    }

    @Override
    public boolean isReady(long gameLoop) {
        return true;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
