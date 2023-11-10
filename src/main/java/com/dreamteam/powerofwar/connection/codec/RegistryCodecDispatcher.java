package com.dreamteam.powerofwar.connection.codec;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecException;
import com.dreamteam.powerofwar.connection.Message;

/**
 * Main implementation of {@link CodecDispatcher} which uses registration of Codecs.
 */
public class RegistryCodecDispatcher implements CodecDispatcher {

    private final Map<Integer, Decoder<?>> decoderByCode = new HashMap<>();
    private final Map<Class<? extends Message>, Encoder<?>> encoderByMessageType = new HashMap<>();

    public RegistryCodecDispatcher() {}

    public RegistryCodecDispatcher(List<Codec<?>> codecs) {
        codecs.forEach(this::register);
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> void register(Codec<T> codec) {
        if (codec instanceof Encoder<?>) {
            ParameterizedType genericInterface = (ParameterizedType) codec.getClass().getGenericSuperclass();
            Class<T> actualTypeArgument = (Class<T>) genericInterface.getActualTypeArguments()[0];
            encoderByMessageType.put(actualTypeArgument, (Encoder<?>) codec);
        }
        if (codec instanceof Decoder<?>) {
            decoderByCode.put(codec.getOPCode().getCode(), (Decoder<?>) codec);
        }
    }

    @Override
    public Message decode(ByteBuffer byteBuffer) {
        for (int i = 0; i < Integer.BYTES; i++) {
            byteBuffer.get();
        }
        int code = byteBuffer.get();
        Decoder<?> codec = decoderByCode.get(code);
        if (codec == null) {
            throw new IllegalCodecException("Decoder with code [" + code + "] has not been found.");
        }

        return codec.decode(byteBuffer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> byte[] encode(T message) {
        Encoder<T> codec = (Encoder<T>) encoderByMessageType.get(message.getClass());
        if (codec != null) {
            return codec.encode(message);
        }
        throw new IllegalCodecException("Encoder of message type [" + message.getClass() + "] has not been found.");
    }
}
