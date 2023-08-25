package com.example.baygo.db.model.enums;

public enum TypeOfProduct {
    ORDINARY("Обычные"),
    WITH_SPECIAL_DELIVERY_CONDITIONS("С особыми условиями доставки");

    private final String displayName;

    TypeOfProduct(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
