package com.modwiz.ld31.utils.assets;

import java.util.HashMap;
import java.util.Map;

public enum LevelString {
    PLAYER("TEUZOD"),
    CREATURE("TEUQOD"),
    BLOCK("TEPZOW"),
    TEXT("TTPZTW"),
    MESSAGE("MERGRY"),
    OBJECT("LEPZOQ"),
    ENEMY("LEPZVQ"),
    DIMENSION_CHANGE_BLOCK("DDDIII"),
    DNA_REPAIR_CELL("EQOWOE"),
    RADIATION_SUCKER("PLKJMN"),
    DIM_START("TYPZOW"),
    DIM_END("TWPZOW"),
    DIM_INITIAL_DIM("TBPZOW"),
    SEP("$");

    public final String string;

    private static final Map<String, LevelString> map = new HashMap<>();

    static {
        buildMap();
    }

    private LevelString(String string) {
        this.string = string;
    }

    private static void buildMap() {
        for (LevelString string : LevelString.values()) {
            map.put(string.string, string);
        }
    }

    public static LevelString parseEncoded(String encoded) {
        return map.get(encoded);
    }

    @Override
    public String toString() {
        return string;
    }

}
