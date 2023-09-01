package com.example.baygo.db.model.enums;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String russianValue;

    Gender(String russianValue) {
        this.russianValue = russianValue;
    }

    public String getRussianValue() {
        return russianValue;
    }
}
