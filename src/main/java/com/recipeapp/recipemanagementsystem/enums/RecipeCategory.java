package com.recipeapp.recipemanagementsystem.enums;

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

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
