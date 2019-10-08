package com.dreamteam.powerofwar.connection.message;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Implementations are used to encode and decode {@link Message Messages} to {@link ByteBuffer Byte Buffers}.
 *
 * @param <T> {@link Message Message} implementation.
 */
public interface Codec<T extends Message> {

    /**
     * Decodes the message from the specified byte buffer.
     * The process starts from the current position of the specified byte buffer.
     *
     * @param byteBuffer the buffer to read from.
     * @return the decoded message.
     * @throws IOException if buffer doesn't have enough bytes to read.
     */
    T decode(ByteBuffer byteBuffer) throws IOException;

    /**
     * Encodes the message to the specified byte buffer.
     * The process starts from the current position of the specified byte array.
     *
     * @param byteBuffer the buffer to write to.
     * @param message the message to encode.
     * @return the buffer after writing.
     * @throws IOException if buffer doesn't have enough bytes to write or any message data fails to encode.
     */
    ByteBuffer encode(ByteBuffer byteBuffer, T message) throws IOException;

    //todo: JavaDocs
    class CodecRegistration {
        private final int opcode;
        private final Codec<?> codec;

        public CodecRegistration(int opcode, Codec<?> codec) {
            this.opcode = opcode;
            this.codec = codec;
        }

        public int getOpcode() {
            return opcode;
        }

        public <M extends Message> Codec<M> getCodec() {
            return (Codec<M>) codec;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + this.opcode;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final CodecRegistration other = (CodecRegistration) obj;
            return this.opcode == other.opcode;
        }
    }
}
