package com.dreamteam.powerofwar.client.message;

import com.dreamteam.powerofwar.connection.codec.OPCode;

public enum MessageTypes implements OPCode {
    GAME_STATE(1),
    ADD_OBJECT(2),
    ;
    private final int code;

    MessageTypes(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}