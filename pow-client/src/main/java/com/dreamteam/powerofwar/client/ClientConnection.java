package com.dreamteam.powerofwar.client;

import java.io.Closeable;

import com.dreamteam.powerofwar.common.handler.Dispatcher;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.session.Session;

public class ClientConnection implements Closeable {

    private Session session;
    private Thread serverListener;

    public ClientConnection(Session session, Dispatcher<Message> messageDispatcher) {
        this.session = session;
        serverListener = new Thread(() -> {
            while (true) {
                Message message = this.session.receiveMessage();
                if (message != null) {
                    messageDispatcher.dispatch(message);
                }
            }
        });
        serverListener.start();
    }

    @Override
    public void close() {
        serverListener.interrupt();
        session.disconnect();
    }

    public void sendMessage(Message message) {
        try {
            session.send(message);
        } catch (ConnectionClosedException e) {
            System.out.println("Connection has been closed");
        }
    }
}
