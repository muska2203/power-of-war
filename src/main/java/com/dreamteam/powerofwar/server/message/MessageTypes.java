package com.dreamteam.powerofwar.server.message;

import com.dreamteam.powerofwar.connection.codec.OPCode;

public enum MessageTypes implements OPCode {
    GAME_STATE(1),
    ADD_OBJECT(2),
    ;
    private int code;

    MessageTypes(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
