package com.example.baygo.db.model.enums;

public enum Role {
    ADMIN("Администратор"),
    IN_REQUEST("На рассмотрении"),
    SELLER("Продавец"),
    BUYER("Покупатель");

    private final String russianValue;

    Role(String russianValue) {
        this.russianValue = russianValue;
    }

    public String getRussianValue() {
        return russianValue;
    }
}
