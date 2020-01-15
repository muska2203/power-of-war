package com.dreamteam.powerofwar.connection.message.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.message.CodecDispatcher;
import com.dreamteam.powerofwar.connection.message.Decoder;
import com.dreamteam.powerofwar.connection.message.Encoder;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.MessageDispatcher;
import com.dreamteam.powerofwar.connection.message.MessageMappingRegisterer;

//todo: JavaDocs
public class ChannelSession implements Session {

    public static final int MAX_MESSAGE_SIZE = 64;
    private SocketChannel channel;
    private MessageDispatcher messageDispatcher;
    private CodecDispatcher codecDispatcher;
    private MessageMappingRegisterer registerer;
    private ByteBuffer buffer = ByteBuffer.allocate(256);

    public ChannelSession(SocketChannel channel, MessageDispatcher messageDispatcher, CodecDispatcher codecDispatcher, MessageMappingRegisterer registerer) {
        this.channel = channel;
        this.messageDispatcher = messageDispatcher;
        this.codecDispatcher = codecDispatcher;
        this.registerer = registerer;
        onReady();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> void receiveMessage() {
        int numRead;
        try {
            do {
                buffer.clear();
                numRead = this.channel.read(buffer);
                if (numRead == -1) {
                    disconnect();
                    return;
                }
                buffer.rewind();
                byte code = buffer.get();
                Decoder<T> decoder = (Decoder<T>) this.codecDispatcher.findDecoder(registerer.getMessageTypeByCode((int) code));
                T message = decoder.decode(buffer);
                this.messageDispatcher.dispatch(message);
            }
            while (numRead == buffer.capacity());
        } catch (IOException ignore) {
            //todo: handle
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends Message> void send(T message) throws ConnectionClosedException {
        if (!this.channel.isOpen()) {
            throw new ConnectionClosedException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);
        try {
            buffer.put(registerer.getCodeByMessageType(message.getClass()).byteValue());
            Encoder<T> codec = (Encoder<T>) this.codecDispatcher.findEncoder(message.getClass());
            codec.encode(buffer, message);
            buffer.rewind();
            this.channel.write(buffer);
        } catch (IOException ignore) {
            //todo: handle
        }
    }

    @Override
    public void sendAll(Message... messages) throws ConnectionClosedException {
        if (!this.channel.isOpen()) {
            throw new ConnectionClosedException();
        }
        ByteBuffer buffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE * messages.length);
        try {
            for (Message message : messages) {
                send(message);
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
            this.channel.close();
        } catch (IOException ignore) {
            //todo: handle
        }
    }
}
