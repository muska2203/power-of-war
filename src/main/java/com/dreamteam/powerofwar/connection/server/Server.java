package com.dreamteam.powerofwar.connection.server;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private ServerConnection serverConnection;
    private Board board;
    private GameProgram gameProgram;

    public Server(String ip, int port) throws IOException {
        serverConnection = new ServerConnection(new InetSocketAddress(ip, port));
    }

    public void start() throws IOException {
        try {
            serverConnection.waitForPlayers();
            board = new Board(800, 480);
            gameProgram = new GameProgram(board);
            gameProgram.startGame();
            serverConnection.startListeningPlayers(gameProgram);
            serverConnection.startMailingBoardInfoToPlayers(board);
        } finally {
            serverConnection.close();
        }
    }
}
