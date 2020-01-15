package com.dreamteam.powerofwar.connection.message.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.dreamteam.powerofwar.connection.exception.ChannelClosedException;
import com.dreamteam.powerofwar.connection.message.Codec;
import com.dreamteam.powerofwar.connection.message.CodecLookupService;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.MessageDispatcher;

//todo: JavaDocs
public class BaseSession implements Session {

    public static final int MAX_MESSAGE_SIZE = 64;
    private SocketChannel channel;
    private MessageDispatcher messageDispatcher;
    private CodecLookupService codecLookupService;

    public BaseSession(SocketChannel channel, MessageDispatcher messageDispatcher, CodecLookupService codecLookupService) {
        this.channel = channel;
        this.messageDispatcher = messageDispatcher;
        this.codecLookupService = codecLookupService;
        onReady();
    }

    @Override
    public <T extends Message> void messageReceived(T message) {
        messageDispatcher.dispatch(message);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> void send(T message) throws ChannelClosedException {
        if (!channel.isOpen()) {
            throw new ChannelClosedException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);
        try {
            Codec<T> codec = (Codec<T>) codecLookupService.find(message.getClass());
            codec.encode(buffer, message);
            channel.write(buffer);
        } catch (IOException ignore) {
            //todo: handle
        }
    }

    @Override
    public void sendAll(Message... messages) throws ChannelClosedException {
        if (!channel.isOpen()) {
            throw new ChannelClosedException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE * messages.length);
        try {
            for (Message message : messages) {
                codecLookupService.find(message.getClass()).encode(buffer, message);
            }
            channel.write(buffer);
        } catch (IOException ignore) {
            //todo: handle
        }
    }

    @Override
    public void disconnect() {
        onDisconnect();
        try {
            channel.close();
        } catch (IOException ignore) {
            //todo: handle
        }
    }
}
