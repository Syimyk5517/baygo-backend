package com.example.baygo.db.model.enums;

public enum ShippingType {
    BY_CARRIER_BAIGO("Курьером БайГо "),
    SELF_SHIP("Самостоятельно");
    private final String displayName;

    ShippingType(String displayName) {
        this.displayName = displayName;


    }
    public String getDisplayName() {
        return displayName;
    }
}
