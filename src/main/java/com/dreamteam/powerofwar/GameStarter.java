package com.dreamteam.powerofwar;

public class GameStarter {
    public static void main(String[] args) {
        Board board = new Board(800, 480);
        board.addGameObject(new StaticGameObject(-0.4, -0.4));
        board.addGameObject(new StaticGameObject(-0.2, -0.2));
        board.addGameObject(new StaticGameObject(0, 0));
        board.addGameObject(new StaticGameObject(0.2, 0.2));
        new GameRender(board).run();
    }
}
