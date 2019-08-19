package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;
import com.dreamteam.powerofwar.game.user.User;

public class AddGameObjectAction implements Action {

    private Board board;
    private GameObjectCreator creator;
    private double x;
    private double y;
    private User owner;

    public AddGameObjectAction(Board board, double x, double y, GameObjectCreator creator, User owner) {
        this.board = board;
        this.creator = creator;
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    @Override
    public void execute() {
        board.addGameObject(creator.create(x, y, owner));

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
