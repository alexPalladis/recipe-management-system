package com.recipeapp.recipemanagementsystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecipeCategory {
    APPETIZER("Ορεκτικό"),
    MAIN_COURSE("Κυρίως Πιάτο"),
    DESSERT("Επιδόρπιο"),
    SALAD("Σαλάτα"),
    SNACK("Σνακ");

    private final String displayName;

    RecipeCategory(String displayName) {
        this.displayName = displayName;
    }

    @JsonCreator
    public static RecipeCategory from(String value) {
        if (value == null) return null;

        return switch (value.toUpperCase()) {
            case "APPETIZER", "ΟΡΕΚΤΙΚΟ" -> APPETIZER;
            case "MAIN_COURSE", "ΚΥΡΙΩΣ ΠΙΑΤΟ" -> MAIN_COURSE;
            case "DESSERT", "ΕΠΙΔΟΡΠΙΟ" -> DESSERT;
            case "SALAD", "ΣΑΛΑΤΑ" -> SALAD;
            case "SNACK", "ΣΝΑΚ" -> SNACK;
            default -> throw new IllegalArgumentException("Unknown category: " + value);
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

