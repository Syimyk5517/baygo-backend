package com.example.baygo.db.model.enums;

public enum TypeOfSupplier {
    WITH_MY_OWN_RESOURCES("Своими силами"),
    DIFFERENT_CARRIER("Другой перевозчик");

    private final String displayName;

    TypeOfSupplier(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
