package com.dreamteam.powerofwar.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.connection.session.Session;
import com.dreamteam.powerofwar.handler.Dispatcher;
import com.dreamteam.powerofwar.server.game.event.AddPlayerEvent;
import com.dreamteam.powerofwar.server.game.event.EventListener;
import com.dreamteam.powerofwar.server.game.player.Player;
import com.dreamteam.powerofwar.server.message.PlayerMessage;

public abstract class ServerConnection implements Runnable, Closeable {

    private Thread serverConnectionThread = new Thread(this);
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private Map<Player, Session> sessions = new ConcurrentHashMap<>();
    private Map<SocketChannel, Player> players = new ConcurrentHashMap<>();

    private final Dispatcher<Message> messageDispatcher;
    private final EventListener eventListener;

    public ServerConnection(Dispatcher<Message> messageDispatcher, EventListener eventListener) throws IOException {
        this.messageDispatcher = messageDispatcher;
        this.eventListener = eventListener;
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(ConnectionInfo.IP, ConnectionInfo.PORT));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
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
        } catch (IOException e) {
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
            //TODO: Задержка сделана, как костыль, чтобы приложение клиента успело включится перед началом работы
            Thread.sleep(1000);
            Session session = createChannelSession(channel);
            Player player = new Player("Player " + players.size());
            eventListener.registerEvent(new AddPlayerEvent(player));
            players.put(channel, player);
            sessions.put(player, session);
        } catch (IOException e) {
            System.out.println("Connection refused.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleRead(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        Player player = players.get(channel);
        Session session = sessions.get(player);
        Message message = session.receiveMessage();
        if (message instanceof PlayerMessage) {
            ((PlayerMessage) message).setInitiator(player);
        }
        messageDispatcher.dispatch(message);
    }

    @Override
    public void close() throws IOException {
        serverSocketChannel.close();
        selector.close();
        serverConnectionThread.interrupt();
    }

    abstract ChannelSession createChannelSession(SocketChannel socketChannel);

    public void sendMessage(Message message) {
        List<Player> closedChannels = new ArrayList<>();
        for (Map.Entry<Player, Session> entry : sessions.entrySet()) {
            try {
                entry.getValue().send(message);
            } catch (ConnectionClosedException e) {
                closedChannels.remove(entry.getKey());
            }
        }
        closedChannels.forEach(sessions::remove);
    }

    public void sendMessage(Message message, Player player) {
        Session session = sessions.get(player);
        if (session != null) {
            session.send(message);
        }
    }
}
