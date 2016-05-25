package com.majorpotato.steampowered.util;

public enum DebugType {
    UNKNOWN("UNKNOWN"),
    AURA("aura"),

    ;

    private String name;
    private DebugType(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public static DebugType getDebugTypeFromName(String name) {
        for(DebugType type : DebugType.values()) {
            if(type.name.equalsIgnoreCase(name)) return type;
        }
        return UNKNOWN;
    }
}
