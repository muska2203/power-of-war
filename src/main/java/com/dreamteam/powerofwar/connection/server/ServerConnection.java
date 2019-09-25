package com.dreamteam.powerofwar.connection.server;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.event.EventListener;
import com.dreamteam.powerofwar.game.player.Player;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConnection implements Closeable {

    public static final int MAX_CONNECTIONS = 2;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(256);

    private Map<SocketChannel, Player> players = new ConcurrentHashMap<>();

    public ServerConnection(InetSocketAddress socketAddress) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(socketAddress);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void waitForPlayers() throws IOException {
        Iterator<SelectionKey> keys;
        SelectionKey key;
        while (players.size() != MAX_CONNECTIONS) {
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

    public void startListeningPlayers(EventListener eventListener) {
        new Thread(() -> {
            try {
                Iterator<SelectionKey> keys;
                SelectionKey key;
                while (serverSocketChannel.isOpen() && players.size() == MAX_CONNECTIONS) {
                    selector.select();
                    keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        key = keys.next();
                        keys.remove();

                        if (key.isValid() && key.isReadable()) {
                            handleRead(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void startMailingBoardInfoToPlayers(Board board) {
        try {
            while (serverSocketChannel.isOpen() && players.size() == MAX_CONNECTIONS) {
                Thread.sleep(1000);
                ByteBuffer buffer = ByteBuffer.wrap(Integer.toString(board.getGameObjects().size()).getBytes());
                for (SocketChannel channel : players.keySet()) {
                    buffer.mark();
                    channel.write(buffer);
                    buffer.reset();
                }
                buffer.clear();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key) {
        try {
            SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
            String address = channel.socket().getInetAddress().toString() + ":" + channel.socket().getPort();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("accepted connection from: " + address);
            players.put(channel, new Player("player" + (players.size() + 1)));
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
            players.remove(channel);
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

    @Override
    public void close() throws IOException {
        serverSocketChannel.close();
        selector.close();
    }
}
