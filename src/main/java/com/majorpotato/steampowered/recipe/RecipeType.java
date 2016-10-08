package com.majorpotato.steampowered.recipe;


public enum RecipeType {
    GENERIC("Generic"),
    ALLOY_SMELTING("Alloy Smelting"),
    ;

    private String name;
    private RecipeType(String name) { this.name = name; }

    public String getName() { return name; }
}
