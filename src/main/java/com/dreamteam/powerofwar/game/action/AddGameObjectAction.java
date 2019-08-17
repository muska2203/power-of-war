package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.GameObjectCreator;

public class AddGameObjectAction implements Action {

    private Board board;
    private GameObjectCreator creator;
    private double x;
    private double y;

    public AddGameObjectAction(Board board, double x, double y, GameObjectCreator creator) {
        this.board = board;
        this.creator = creator;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        board.addGameObject(creator.create(x, y));

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
