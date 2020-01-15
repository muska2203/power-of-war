package com.dreamteam.powerofwar.connection.message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecCodeException;

public class StandardEncoderDispatcher implements EncoderDispatcher {

    private Map<Class<? extends Message>, Encoder<?>> encoders;

    public StandardEncoderDispatcher(Collection<Encoder<?>> encoders) {
        this.encoders = new HashMap<>();
        for (Encoder<?> encoder : encoders) {
            this.encoders.put(encoder.getHandledClass(), encoder);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> Encoder<T> findEncoder(Class<T> messageType) throws IllegalCodecCodeException {
        return (Encoder<T>) encoders.get(messageType);
    }
}
