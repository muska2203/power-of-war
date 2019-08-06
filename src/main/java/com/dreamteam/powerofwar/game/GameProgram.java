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
    private boolean running;

    public GameProgram(Board board) {
        this.board = board;
    }

    public synchronized void startGame() {
        running = true;
        gameThread.start();
    }

    public synchronized void stopGame() {
        running = false;
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
        while (running) {
            doEvents();
            Long now = System.nanoTime();
            if (lastUpdate == null) {
                lastUpdate = now;
            }
            long loopTime = now - lastUpdate;

            board.getGameObjects().forEach((gameObject -> gameObject.update(board, loopTime)));
            board.cleanOverboardObjects();
            board.cleanDeadObjects();
            lastUpdate = now;
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                this.stopGame();
            }
        }
    }
}
