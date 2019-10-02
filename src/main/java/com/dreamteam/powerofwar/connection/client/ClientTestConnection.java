package com.dreamteam.powerofwar.connection.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.connection.message.type.CloseConnectionMessage;

public class ClientTestConnection {

    private static ClientConnection clientConnection;
    private static BufferedReader consoleReader;

    public static void main(String[] args) {
        try {
            ClientTestConnection client = new ClientTestConnection(ConnectionInfo.IP, ConnectionInfo.PORT);
            client.start();
        } catch (IOException e) {
            System.out.println("Connection has been refused.");
        }
    }

    public ClientTestConnection(String ip, int port) throws IOException {
        clientConnection = new ClientConnection(new InetSocketAddress(ip, port));
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() throws IOException {
        try {
//            clientConnection.startListeningServer(System.out::println);
            while (clientConnection.isOpen()) {
                String word = consoleReader.readLine();
                if (word.equals("exit")) {
                    clientConnection.sendMessage(new CloseConnectionMessage(0));
//                    clientConnection.close();
                    return;
                }
            }
        } finally { // в любом случае необходимо закрыть сокет и потоки
            if (clientConnection.isOpen()) {
                System.out.println("Client has been closed");
                clientConnection.close();
            }
        }
    }

}
