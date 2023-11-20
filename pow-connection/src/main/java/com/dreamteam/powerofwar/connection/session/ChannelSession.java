package com.dreamteam.powerofwar.connection.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;

/**
 * Standard implementation of the {@link Session} which uses a socket channel to connect to another program.
 */
public class ChannelSession implements Session {

    public static final int MAX_MESSAGE_SIZE = 1 << 10;

    // todo [vgamezo]: use UUID
    private static int ID_GENERATOR = 0;

    private final ChunkReader chunkReader;
    private final ByteBuffer readBuffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);

    private final SocketChannel channel;
    private final CodecDispatcher codecDispatcher;
    private final int id;

    public ChannelSession(SocketChannel channel, CodecDispatcher codecDispatcher) {
        this.id = ++ID_GENERATOR;
        this.channel = channel;
        this.chunkReader = new ChunkReader();
        this.codecDispatcher = codecDispatcher;
        onReady();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> T receiveMessage() {
        try {
            while (true) {
                readBuffer.rewind();
                int readCount = this.channel.read(readBuffer);
                if (readCount == -1) {
                    break;
                }
                byte[] array = Arrays.copyOf(readBuffer.array(), readCount);
                chunkReader.addChunk(array);
                byte[] readyToParseChunk = chunkReader.getReadyToParseChunk();
                if (readyToParseChunk != null) {
                    return (T) this.codecDispatcher.decode(ByteBuffer.wrap(readyToParseChunk));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends Message> void send(T message) throws ConnectionClosedException {
        if (!this.channel.isOpen()) {
            throw new ConnectionClosedException();
        }
        byte[] encoded = this.codecDispatcher.encode(message);
        writeBuffer = ByteBuffer.wrap(encoded);
        try {
            while (writeBuffer.hasRemaining()) {
                this.channel.write(writeBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: handle
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
        } catch (IOException e) {
            e.printStackTrace();
            //todo: handle
        }
    }

    @Override
    public int getId() {
        return this.id;
    }
}
