package com.dreamteam.powerofwar.game.object.type;

import java.util.Arrays;

import static com.dreamteam.powerofwar.game.object.type.ObjectFunctionType.BUILDING;
import static com.dreamteam.powerofwar.game.object.type.ObjectFunctionType.RESOURCE;
import static com.dreamteam.powerofwar.game.object.type.ObjectFunctionType.UNIT;

public enum GameObjectType {
    BASE(1, BUILDING),
    GOLD_MINE(2, RESOURCE),
    WARRIOR(3, UNIT),
    COWARD(4, UNIT),
    GOLD_MINER(5, UNIT);

    private int code;
    private ObjectFunctionType functionType;

    GameObjectType(int code, ObjectFunctionType functionType) {
        this.code = code;
        this.functionType = functionType;
    }

    public int getCode() {
        return code;
    }

    public static GameObjectType valueOf(int code) {
        return Arrays.stream(values())
                .filter(type -> type.code == code)
                .findFirst()
                .orElse(null);
    }

    public ObjectFunctionType getFunctionType() {
        return functionType;
    }
}
