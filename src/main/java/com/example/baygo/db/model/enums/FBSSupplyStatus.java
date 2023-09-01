package com.example.baygo.db.model.enums;

public enum FBSSupplyStatus {
    ACCEPTED("Принято"),
    DELIVERY("Доставлено"),
    CANCELED("Отменено"),
    RECEIVED("Получено");
    private final String displayName;



    FBSSupplyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
