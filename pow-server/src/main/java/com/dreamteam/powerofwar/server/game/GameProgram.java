package com.dreamteam.powerofwar.server.game;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.server.game.event.Event;
import com.dreamteam.powerofwar.server.game.event.EventListener;
import com.dreamteam.powerofwar.server.game.object.Board;
import com.dreamteam.powerofwar.server.game.object.GameObject;

public class GameProgram implements EventListener, Runnable {

    private final Board board;
    private final Thread gameThread = new Thread(this);
    private Long lastUpdate = null;
    private final Queue<Event> events = new ConcurrentLinkedDeque<>();
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

    public void addPlayer(Player player) {
        board.addPlayer(player);
    }

    public GameObject createObject(double x, double y, GameObjectType type, Player owner) {
        return board.createObject(x, y, type, owner);
    }

    @Override
    public void registerEvent(Event event) {
        events.add(event);
    }

    @Override
    public void run() {
        while (running) {
            try {
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
            } catch (Exception exception) {
                exception.printStackTrace();
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
