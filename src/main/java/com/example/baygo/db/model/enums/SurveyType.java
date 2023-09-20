package com.example.baygo.db.model.enums;

public enum SurveyType {
    FOR_BUYERS("Для покупателей"),
    FOR_SELLERS("Для продавцов");

    private final String russianValue;

    SurveyType(String russianValue) {
        this.russianValue = russianValue;
    }

    public String getRussianValue() {
        return russianValue;
    }
}
