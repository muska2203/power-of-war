package com.dreamteam.powerofwar.connection.session;

import java.util.Arrays;

import static com.dreamteam.powerofwar.connection.utils.ArrayUtils.mergeArrays;
import static com.dreamteam.powerofwar.connection.utils.ByteUtils.decodeInt;

public class ChunkReader {

    private byte[] bytes = new byte[0];

    public void addChunk(byte[] chunk) {
        bytes = mergeArrays(bytes, chunk);
    }

    /**
     * @return null is message is not ready, otherwise encoded message as byte array
     */
    public byte[] getReadyToParseChunk() {
        if (bytes.length < Integer.BYTES) {
            // If the count of the message bytes is zero,
            //      the received message will contain only START_MESSAGE.length + Integer.BYTES bytes.
            //      As result the message will be valid. We need receive at least this count of message to start to process it
            return null;
        }

        // trying to get count of the message bytes
        int countBytes = decodeInt(bytes);
        int right = Integer.BYTES + countBytes;

        if (right > this.bytes.length) {
            // if we need more bytes than we have now.
            return null;
        }
        byte[] message = Arrays.copyOf(this.bytes, right);
        if (this.bytes.length == right) {
            this.bytes = new byte[0];
        } else {
            this.bytes = Arrays.copyOfRange(this.bytes, right, this.bytes.length);
        }
        return message;
    }
}
