package com.dreamteam.powerofwar.connection.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.dreamteam.powerofwar.connection.message.Message;

public class RegistryCodecDispatcher implements CodecDispatcher {

    private Map<Integer, Codec<?>> codecByCode = new HashMap<>();
    private Map<Class<? extends Message>, Codec<?>> codecByMessageType = new HashMap<>();

    public void register(Codec<?> codec) {
        codecByCode.put(codec.getOPCode().getCode(), codec);
        codecByMessageType.put(codec.getHandledClass(), codec);
    }

    @Override
    public Message decode(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.mark();
        int code = byteBuffer.get();
        Codec<?> codec = codecByCode.get(code);
        if (codec != null) {
            return codec.decode(byteBuffer);
        }
        byteBuffer.reset();
        throw new IllegalArgumentException("byte buffer is not ready to decode.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> boolean encode(ByteBuffer byteBuffer, T message) throws IOException {
        byteBuffer.mark();
        Codec<T> codec = (Codec<T>) codecByMessageType.get(message.getClass());
        if (codec != null && codec.codingSize() < byteBuffer.limit() - byteBuffer.position()) {
            byteBuffer.put((byte) codec.getOPCode().getCode());
            codec.encode(byteBuffer, message);
            return true;
        }
        byteBuffer.reset();
        return false;
    }
}
