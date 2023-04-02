package com.dreamteam.powerofwar.connection.codec;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecException;
import com.dreamteam.powerofwar.connection.exception.TooSmallBufferSizeException;
import com.dreamteam.powerofwar.connection.Message;

/**
 * Main implementation of {@link CodecDispatcher} which uses registration of Codecs.
 */
public class RegistryCodecDispatcher implements CodecDispatcher {

    private Map<Integer, Decoder<?>> decoderByCode = new HashMap<>();
    private Map<Class<? extends Message>, Encoder<?>> encoderByMessageType = new HashMap<>();

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
        byteBuffer.mark();
        int code = byteBuffer.get();
        Decoder<?> codec = decoderByCode.get(code);
        if (codec != null) {
            if (codec.getCodingSize() > byteBuffer.limit() - byteBuffer.position()) {
                throw new TooSmallBufferSizeException("Byte Buffer must contain at least - " + codec.getCodingSize());
            }
            return codec.decode(byteBuffer);
        }
        byteBuffer.reset();
        throw new IllegalCodecException("Decoder with code [" + code + "] has not been found.");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> boolean encode(ByteBuffer byteBuffer, T message) {
        byteBuffer.mark();
        Encoder<T> codec = (Encoder<T>) encoderByMessageType.get(message.getClass());
        if (codec != null) {
            int messageSize = codec.getMessageSize(message);
            int limit = byteBuffer.capacity() - byteBuffer.position() - Byte.BYTES;
            if (messageSize >= limit) {
                throw new TooSmallBufferSizeException("Byte Buffer must contain at least - " + messageSize + ". You have only " + limit);
            }
            byteBuffer.put((byte) codec.getOPCode().getCode());
            codec.encode(byteBuffer, message);
            return true;
        }
        byteBuffer.reset();
        throw new IllegalCodecException("Encoder of message type [" + message.getClass() + "] has not been found.");
    }
}
