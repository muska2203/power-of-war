package com.dreamteam.powerofwar.connection.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

    private InetSocketAddress hostAddress;
    public static final int MAX_CONNECTIONS = 2;
    public int connectionsCount = 0;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(256);

    public Server(String ip, int port) {
        hostAddress = new InetSocketAddress(ip, port);
    }

    public void start() throws IOException {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(hostAddress);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            prepareGame();
            startGame();

        } finally {
            serverSocketChannel.close();
        }
    }

    private void prepareGame() throws IOException {
        Iterator<SelectionKey> keys;
        SelectionKey key;
        while (connectionsCount != MAX_CONNECTIONS) {
            selector.select();
            keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                key = keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    handleAccept(key);
                }
            }
        }
    }

    private void startGame() throws IOException {
        Iterator<SelectionKey> keys;
        SelectionKey key;
        while (serverSocketChannel.isOpen() && connectionsCount == MAX_CONNECTIONS) {
            selector.select();
            keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                key = keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
            }

        }
    }

    private void handleAccept(SelectionKey key) {
        try {
            SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
            String address = channel.socket().getInetAddress().toString() + ":" + channel.socket().getPort();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("accepted connection from: " + address);
            connectionsCount++;
        } catch (IOException e) {
            System.out.println("Connection refused.");
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        buffer.clear();
        int numRead = -1;
        try {
            numRead = channel.read(buffer);
        } catch (IOException ignored) {
        }
        if (numRead == -1) {
            connectionsCount--;
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            System.out.println("Connection closed by client: " + remoteAddr);
            channel.close();
            key.cancel();
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);

        String msg = new String(data);

        registerMessage(msg);
    }

    private void registerMessage(String message) {
        System.out.println(message);
    }
}
