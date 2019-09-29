package com.dreamteam.powerofwar.connection.server;

import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private ServerConnection serverConnection;
    private Board board;
    private GameProgram gameProgram;

    public static void main(String[] args) {
        try {
            Server server = new Server(ConnectionInfo.IP, ConnectionInfo.PORT);
            server.start();
        } catch (IOException e) {
            System.out.println("Server has been closed.");
        }
    }

    public Server(String ip, int port) throws IOException {
        serverConnection = new ServerConnection(new InetSocketAddress(ip, port));
    }

    public void start() throws IOException {
        try {
            Thread serverThread = new Thread(serverConnection);
            board = new Board();
            gameProgram = new GameProgram(board);
            gameProgram.startGame();
            serverThread.start();
            serverConnection.startMailingBoardInfoToPlayers(board);
        } finally {
            serverConnection.close();
        }
    }
}
