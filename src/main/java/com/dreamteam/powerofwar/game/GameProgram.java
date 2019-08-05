package com.dreamteam.powerofwar.game;


import com.dreamteam.powerofwar.game.event.Event;
import com.dreamteam.powerofwar.game.event.EventListener;
import com.dreamteam.powerofwar.game.object.GameObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameProgram implements EventListener, Runnable {

    private Board board;
    private Thread gameThread = new Thread(this);
    private Long lastUpdate = null;
    private Queue<Event> events = new ConcurrentLinkedDeque<>();

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

    public void addGameObject(GameObject gameObject) {
        board.addGameObject(gameObject);
    }

    private void doEvents() {
        while (!events.isEmpty()) {
            events.poll().execute(this);
        }
    }

    @Override
    public void registerEvent(Event event) {
        events.add(event);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            doEvents();
            Long now = System.nanoTime();
            if (lastUpdate == null) {
                lastUpdate = now;
            }
            long loopTime = now - lastUpdate;

            board.getGameObjects().forEach((gameObject -> gameObject.doAction(board, loopTime)));
            board.cleanOverboardObjects();
            board.checkCollisions();
            lastUpdate = now;
            System.out.println(board.getGameObjects().size());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                this.stopGame();
            }
        }
    }
}
