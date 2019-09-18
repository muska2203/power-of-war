package com.dreamteam.powerofwar.connection;

import com.dreamteam.powerofwar.connection.client.Client;
import com.dreamteam.powerofwar.connection.server.Server;

import java.io.IOException;

public class TestConnection {

    private static final String IP = "localhost";
    private static final int PORT = 8081;

    /**
     * Чтобы запустить сервер, требуется указать строку "server" аргументом при запуске.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("server")) {
            Server server = new Server(IP, PORT);
            try {
                server.start();
            } catch (IOException e) {
                System.out.println("Server has been closed.");
            }
        } else {
            Client client = new Client(IP, PORT);
            try {
                client.start();
            } catch (IOException e) {
                System.out.println("Connection has been refused.");
            }
        }
    }
}
