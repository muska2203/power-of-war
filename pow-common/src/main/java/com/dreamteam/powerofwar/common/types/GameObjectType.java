package com.dreamteam.powerofwar.common.types;

import java.util.Arrays;

public enum GameObjectType {
    BASE(1, ObjectFunctionType.BUILDING),
    GOLD_MINE(2, ObjectFunctionType.RESOURCE),
    WARRIOR(3, ObjectFunctionType.UNIT),
    COWARD(4, ObjectFunctionType.UNIT),
    GOLD_MINER(5, ObjectFunctionType.UNIT),
    ;

    private final int code;
    private final ObjectFunctionType functionType;

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
