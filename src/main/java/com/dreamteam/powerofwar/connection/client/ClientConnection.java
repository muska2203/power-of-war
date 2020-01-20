package com.dreamteam.powerofwar.connection.client;

import java.io.Closeable;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.session.Session;

@Component
public class ClientConnection implements Closeable {

    private Session session;
    private Thread serverListener;

    public ClientConnection(Session session) {
        this.session = session;
        serverListener = new Thread(() -> {
            while (true) {
                this.session.receiveMessage();
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
