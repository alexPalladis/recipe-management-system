package com.recipeapp.recipemanagementsystem.enums;


public enum Difficulty {
    EASY("Εύκολο"),
    MEDIUM("Μεσαίο"),
    HARD("Δύσκολο");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
