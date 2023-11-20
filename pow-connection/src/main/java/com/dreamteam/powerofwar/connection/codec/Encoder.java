package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.utils.ArrayUtils;
import com.dreamteam.powerofwar.connection.utils.ByteUtils;

/**
 * Implementations can encode the specified type of messages.
 *
 * @param <T> message type
 */
public abstract class Encoder<T extends Message> implements Codec<T> {

    /**
     * Encodes the message to the specified byte array.
     * The process starts from the current position of the specified byte array.
     *
     * @param message a message to encode.
     * @return the buffer after writing.
     */
    public byte[] encode(T message) {
        ByteBuffer buffer = ByteBuffer.allocate(getMessageSize(message));
        write(message, buffer);
        byte[] mainMessage = buffer.array();

        return ArrayUtils.mergeArrays(
                ByteUtils.encodeInt(mainMessage.length + OPCode.OP_CODE_SIZE),
                new byte[] {(byte) getOPCode().getCode()},
                mainMessage
        );
    }

    protected abstract void write(T message, ByteBuffer buffer);

    public abstract int getMessageSize(T message);
}
