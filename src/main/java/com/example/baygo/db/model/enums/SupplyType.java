package com.example.baygo.db.model.enums;

public enum SupplyType {
    BOX("Короба"),
    MONO_PALLETS("Монопаллет"),
    SUPER_SAFE("Суперсейф");

    private final String displayName;

    SupplyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
