package com.dreamteam.powerofwar.connection.client;

import java.io.*;
import java.net.InetSocketAddress;

public class Client {

    private static ClientConnection clientConnection;
    private static BufferedReader consoleReader;

    public Client(String ip, int port) throws IOException {
        clientConnection = new ClientConnection(new InetSocketAddress(ip, port));
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() throws IOException {
        try {
            clientConnection.startListeningServer(System.out::println);
            while (clientConnection.isOpen()) {
                String word = consoleReader.readLine();
                clientConnection.write(word);
            }
        } finally { // в любом случае необходимо закрыть сокет и потоки
            if (clientConnection.isOpen()) {
                System.out.println("Client has been closed");
                clientConnection.close();
            }
        }
    }

}
