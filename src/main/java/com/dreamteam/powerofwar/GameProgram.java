package com.dreamteam.powerofwar;


public class GameProgram {

    private Board board;
    private Thread gameThread = new Thread(this::loop);
    private Long lastUpdate = null;

    public GameProgram(Board board) {
        this.board = board;
    }

    public void startGame() {
        gameThread.start();
    }

    public void stopGame() {
        if (gameThread.isAlive()) {
            gameThread.interrupt();
        }
    }

    private void loop() {
        Long now = System.nanoTime();
        if (lastUpdate == null) {
            lastUpdate = now;
        }
        long loopTime = now - lastUpdate;

        board.getGameObjects().forEach((gameObject -> gameObject.move(board, loopTime)));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
