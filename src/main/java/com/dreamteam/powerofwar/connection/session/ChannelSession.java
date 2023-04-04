package com.dreamteam.powerofwar.connection.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;

import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.utils.ArrayUtils;

/**
 * Standard implementation of the {@link Session} which uses a socket channel to connect to another program.
 */
public class ChannelSession implements Session {

    private static int ID_GENERATOR = 0;

    public static final int MAX_MESSAGE_SIZE = 1 << 10;
    private final ChunkReader chunkReader;
    ByteBuffer readBuffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);
    ByteBuffer writeBuffer = ByteBuffer.allocate(MAX_MESSAGE_SIZE);

    private SocketChannel channel;
    private CodecDispatcher codecDispatcher;
    private int id;

    public ChannelSession(SocketChannel channel, CodecDispatcher codecDispatcher) {
        this.id = ++ID_GENERATOR;
        System.out.println("ID = " + id);
        this.channel = channel;
        this.chunkReader = new ChunkReader();
        this.codecDispatcher = codecDispatcher;
        onReady();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> T receiveMessage() {
        try {
            readBuffer.clear();
            while (true) {
                int readCount = this.channel.read(readBuffer);
                if (readCount == -1) {
                    break;
                }
                readBuffer.rewind();
                byte[] array = Arrays.copyOf(readBuffer.array(), readCount);
                System.out.println("readCount: " + readCount + ", position: " + readBuffer.capacity());
                chunkReader.addChunk(array);
                byte[] readyToParseChunk = chunkReader.getReadyToParseChunk();
                if (readyToParseChunk != null) {
                    return (T) this.codecDispatcher.decode(ByteBuffer.wrap(readyToParseChunk));
                }
                readBuffer.clear();
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
        List<byte[]> chunks = ArrayUtils.split(encoded, MAX_MESSAGE_SIZE);

        for (byte[] chunk : chunks) {
            writeBuffer = ByteBuffer.wrap(chunk);
            writeBuffer.rewind();
            try {
                if (writeBuffer.hasRemaining()) {
                    this.channel.write(writeBuffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: handle
            }
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
            ignore.printStackTrace();
            //todo: handle
        }
    }

    @Override
    public int getId() {
        return this.id;
    }
}
