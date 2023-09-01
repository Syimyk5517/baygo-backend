package com.example.baygo.db.model.enums;

public enum DayOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String russianValue;

    DayOfWeek(String russianValue) {
        this.russianValue = russianValue;
    }

    public String getRussianValue() {
        return russianValue;
    }
}






