package com.dreamteam.powerofwar;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.object.BaseGameObject;
import com.dreamteam.powerofwar.phisics.Vector;
import com.dreamteam.powerofwar.renderer.GameRenderer;

public class GameStarter {
    public static void main(String[] args) {
        Board board = new Board(800, 480);
//        board.addGameObject(new BaseGameObject(0, 0, new Vector(0.01, 0), 1));
//        board.addGameObject(new BaseGameObject(0, 100, new Vector(0.01, 0), 2));
//        board.addGameObject(new BaseGameObject(0, 200, new Vector(0.01, 0), 3));
//        board.addGameObject(new BaseGameObject(0, 300, new Vector(0.01, 0), 2));
//        board.addGameObject(new BaseGameObject(800, 500, new Vector(0.01, 0), 2));
        GameProgram gameProgram = new GameProgram(board);
        gameProgram.startGame();
        Thread gameRender = new Thread(new GameRenderer(board, gameProgram));
        gameRender.start();
    }
}
