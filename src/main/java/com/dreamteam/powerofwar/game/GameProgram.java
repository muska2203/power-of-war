package com.dreamteam.powerofwar.game;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.game.event.Event;
import com.dreamteam.powerofwar.game.event.EventListener;
import com.dreamteam.powerofwar.game.object.GameObject;

@Component
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


    @Override
    public void registerEvent(Event event) {
        events.add(event);
    }

    @Override
    public void run() {
        while (running) {
            doEvents();
            long loopTime = calculateLoopTime();
            board.doActions(loopTime);

            board.getGameObjects().forEach(gameObject -> gameObject.update(board));
            board.getGameObjects().forEach(gameObject -> gameObject.move(loopTime));
            board.cleanOverboardObjects();
            board.cleanDeadObjects();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                this.stopGame();
            }
        }
    }

    private void doEvents() {
        while (!events.isEmpty()) {
            events.poll().execute(this);
        }
    }


    private long calculateLoopTime() {
        Long now = System.nanoTime();
        if (lastUpdate == null) {
            lastUpdate = now;
        }
        long result = now - lastUpdate;
        lastUpdate = now;
        return result;
    }
}
