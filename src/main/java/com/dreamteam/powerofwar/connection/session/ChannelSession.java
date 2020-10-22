package com.dreamteam.powerofwar.connection.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.handler.Dispatcher;

//todo: JavaDocs
public class ChannelSession implements Session {

    private static int ID_GENERATOR = 0;

    public static final int MAX_MESSAGE_SIZE = 1024;
    ByteBuffer readBuffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);
    ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);

    private SocketChannel channel;
    private Dispatcher<IncomingMessage> messageDispatcher;
    private CodecDispatcher codecDispatcher;
    private int id;

    public ChannelSession(SocketChannel channel, Dispatcher<IncomingMessage> messageDispatcher, CodecDispatcher codecDispatcher) {
        this.id = ++ID_GENERATOR;
        this.channel = channel;
        this.messageDispatcher = messageDispatcher;
        this.codecDispatcher = codecDispatcher;
        onReady();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IncomingMessage> void receiveMessage() {
        int numRead;
        readBuffer.clear();
        try {
            numRead = this.channel.read(readBuffer);
        } catch (IOException ignore) {
            numRead = 0; //todo: Delete it and throw exception
        }
        if (numRead == -1) {
            disconnect();
            return;
        }
        readBuffer.rewind();
        T message = (T) this.codecDispatcher.decode(readBuffer);
        message.setSenderSessionId(getId());
        this.messageDispatcher.dispatch(message);
    }

    @Override
    public <T extends Message> void send(T message) throws ConnectionClosedException {
        if (!this.channel.isOpen()) {
            throw new ConnectionClosedException();
        }
        writeBuffer.clear();
        this.codecDispatcher.encode(writeBuffer, message);
        writeBuffer.rewind();
        try {
            while (writeBuffer.hasRemaining()) {
                this.channel.write(writeBuffer);
            }
        } catch (IOException ignore) {
            //todo: throw exception
        }
    }

    @Override
    public void sendAll(Message... messages) throws ConnectionClosedException {
        for (Message message : messages) {
            send(message);
        }
    }

    @Override
    public void disconnect() {
        onDisconnect();
        try {
            this.channel.close();
        } catch (IOException ignore) {
            //todo: handle
        }
    }

    @Override
    public int getId() {
        return this.id;
    }
}
