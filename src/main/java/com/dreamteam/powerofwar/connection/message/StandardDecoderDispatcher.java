package com.dreamteam.powerofwar.connection.message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecCodeException;

public class StandardDecoderDispatcher implements DecoderDispatcher {

    private Map<Class<? extends Message>, Decoder<?>> decoders;

    public StandardDecoderDispatcher(Collection<Decoder<?>> decoders) {
        this.decoders = new HashMap<>();
        for (Decoder<?> decoder : decoders) {
            this.decoders.put(decoder.getHandledClass(), decoder);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Message> Decoder<T> findDecoder(Class<T> messageType) throws IllegalCodecCodeException {
        return (Decoder<T>) decoders.get(messageType);
    }
}
