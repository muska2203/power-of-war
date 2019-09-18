package com.dreamteam.powerofwar.connection.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    private InetSocketAddress address;
    private static SocketChannel clientSocket;
    private static BufferedReader consoleReader;

    public Client(String ip, int port) {
        address = new InetSocketAddress(ip, port);
    }

    public void start() throws IOException {
        try {
            clientSocket = SocketChannel.open(address);
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (clientSocket.isOpen()) {
                String word = consoleReader.readLine();
                byte[] bytes = word.getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                clientSocket.write(buffer);
                buffer.clear();
            }
        } finally { // в любом случае необходимо закрыть сокет и потоки
            if (clientSocket != null && clientSocket.isOpen()) {
                System.out.println("Client has been closed");
                clientSocket.close();
            }
        }
    }

}
