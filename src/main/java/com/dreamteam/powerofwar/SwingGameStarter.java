package com.dreamteam.powerofwar;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.game.object.SuicideObject;
import com.dreamteam.powerofwar.renderer.swing.SwingGameRenderer;

public class SwingGameStarter {
    public static void main(String[] args) {
        Board board = new Board(800, 480);
        board.addGameObject(new SuicideObject(0, 0));
        board.addGameObject(new SuicideObject(0, 100));
        board.addGameObject(new SuicideObject(0, 200));
        board.addGameObject(new SuicideObject(0, 300));
        board.addGameObject(new SuicideObject(800, 500));
        GameProgram gameProgram = new GameProgram(board);
        gameProgram.startGame();
        SwingGameRenderer swingGameRenderer = new SwingGameRenderer(board, gameProgram);
        swingGameRenderer.start();
    }
}
