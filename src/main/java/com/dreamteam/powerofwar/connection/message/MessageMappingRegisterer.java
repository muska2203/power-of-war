package com.dreamteam.powerofwar.connection.message;

import java.util.HashMap;
import java.util.Map;

public class MessageMappingRegisterer {

    private Map<Integer, Class<? extends Message>> codeToMessageTypeMap = new HashMap<>();

    public void register(Integer code, Class<? extends Message> messageType) {
        codeToMessageTypeMap.put(code, messageType);
    }

    public Class<? extends Message> getMessageTypeByCode(Integer code) {
        return codeToMessageTypeMap.get(code);
    }

    public Integer getCodeByMessageType(Class<? extends Message> messageType) {
        return codeToMessageTypeMap.entrySet().stream()
                .filter(entry -> messageType.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

}
