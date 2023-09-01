package com.example.baygo.db.model.enums;

public enum SupplyStatus {
    ACCEPTED("Принято"),
    PLANNED("Запланировано"),
    CANCELED("Отменено"),
    DELIVERED("Доставлено");

    private final String displayName;

    SupplyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
