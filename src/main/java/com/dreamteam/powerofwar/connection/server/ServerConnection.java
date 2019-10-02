package com.dreamteam.powerofwar.connection.server;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.MessageDispatcher;

@Component
public class ServerConnection implements Runnable, Closeable {

    private Thread serverConnectionThread = new Thread(this);
    private MessageDispatcher messageDispatcher;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(256);

    private List<SocketChannel> channels = new CopyOnWriteArrayList<>();

    public ServerConnection(MessageDispatcher messageDispatcher) throws IOException {
        this.messageDispatcher = messageDispatcher;
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(ConnectionInfo.IP, ConnectionInfo.PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() {
        serverConnectionThread.start();
    }

    @Override
    public void run() {
        try {
            Iterator<SelectionKey> keys;
            SelectionKey key;
            while (serverSocketChannel.isOpen()) {
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
                    if (key.isValid() && key.isReadable()) {
                        handleRead(key);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
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
            channels.add(channel);
        } catch (IOException e) {
            System.out.println("Connection refused.");
        }
    }

    private void handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel channel = (SocketChannel) key.channel();
        byte[] data = new byte[0];
        int numRead;
        do {
            buffer.clear();
            numRead = channel.read(buffer);
            if (numRead == -1) {
                return;
            }
            byte[] tmp = data;
            data = new byte[tmp.length + numRead];
            System.arraycopy(tmp, 0, data, 0, tmp.length);
            System.arraycopy(buffer.array(), 0, data, tmp.length, numRead);
        }
        while (numRead == buffer.capacity());

        ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(data));
        Message message = (Message) reader.readObject();
        messageDispatcher.dispatch(message);
    }

    @Override
    public void close() throws IOException {
        serverSocketChannel.close();
        selector.close();
        serverConnectionThread.interrupt();
    }

    public void closeChannel(int connectionId) {
        try {
            SocketChannel socketChannel = channels.get(connectionId);
            if (socketChannel != null) {
                channels.remove(socketChannel);
                socketChannel.close();
            }
        } catch (IOException ignore) { }
    }
}
