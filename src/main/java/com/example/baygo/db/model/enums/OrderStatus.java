package com.example.baygo.db.model.enums;

public enum OrderStatus {
    PENDING("В ожидании"),
    ON_ASSEMBLY("На сборке"),
    ON_SUPPLY_DELIVERY("На доставке"),
    ON_WAREHOUSE("На складе"),
    ON_PIO("На ПВЗ"),
    COURIER_ON_THE_WAY("Курьер в пути"),
    DELIVERED("Доставлено"),
    CANCELED("Отменено");

    private final String displayName;



    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
