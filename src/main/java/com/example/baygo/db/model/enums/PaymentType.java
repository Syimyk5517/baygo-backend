package com.example.baygo.db.model.enums;

public enum PaymentType {
    ONLINE_BY_CARD("Онлайн, карта"),
    OFFLINE_BY_CARD("Офлайн, карта"),
    OFFLINE_BY_CASH("Офлайн, наличные");

    private final String russianValue;

    PaymentType(String russianValue) {
        this.russianValue = russianValue;
    }

    public String getRussianValue() {
        return russianValue;
    }

}
