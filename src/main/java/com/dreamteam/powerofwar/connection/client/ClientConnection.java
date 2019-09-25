package com.dreamteam.powerofwar.connection.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientConnection implements Closeable {

    private static SocketChannel clientSocketChannel;

    public ClientConnection(InetSocketAddress inetSocketAddress) throws IOException {
        clientSocketChannel = SocketChannel.open(inetSocketAddress);
    }

    public void write(String message) throws IOException {
        byte[] bytes = message.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        clientSocketChannel.write(buffer);
        buffer.clear();
    }

    public void startListeningServer(ServerListener serverListener) {
        new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
            while (true) {
                    int numRead = clientSocketChannel.read(buffer);
                    byte[] data = new byte[numRead];
                    System.arraycopy(buffer.array(), 0, data, 0, numRead);
                    String message = new String(data);
                    serverListener.registerMessage(message);
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void close() throws IOException {
        clientSocketChannel.close();
    }

    public boolean isOpen() {
        return clientSocketChannel != null && clientSocketChannel.isOpen();
    }
}
