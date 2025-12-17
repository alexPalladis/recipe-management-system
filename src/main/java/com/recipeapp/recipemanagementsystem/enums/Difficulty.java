package com.recipeapp.recipemanagementsystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Difficulty {
    EASY("Εύκολο"),
    MEDIUM("Μεσαίο"),
    HARD("Δύσκολο");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    @JsonCreator
    public static Difficulty from(String value) {
        if (value == null) return null;

        return switch (value.toUpperCase()) {
            case "EASY", "ΕΥΚΟΛΟ" -> EASY;
            case "MEDIUM", "ΜΕΣΑΙΟ" -> MEDIUM;
            case "HARD", "ΔΥΣΚΟΛΟ" -> HARD;
            default -> throw new IllegalArgumentException("Unknown difficulty: " + value);
        };
    }

    @JsonValue
    public String toJson() {
        return name();
    }

    public String getDisplayName() {
        return displayName;
    }
}

