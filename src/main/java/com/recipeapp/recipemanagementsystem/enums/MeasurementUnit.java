package com.recipeapp.recipemanagementsystem.enums;

public enum MeasurementUnit {
    GRAMS("γραμμάρια"),
    KILOGRAMS("κιλά"),
    MILLILITERS("ml"),
    LITERS("λίτρα"),
    CUPS("φλιτζάνια"),
    TABLESPOONS("κουταλιές σούπας"),
    TEASPOONS("κουταλάκια γλυκού"),
    PIECES("κομμάτια"),
    SLICES("φέτες"),
    PINCH("πρέζα");

    private final String displayName;

    MeasurementUnit(String displayName) {
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
