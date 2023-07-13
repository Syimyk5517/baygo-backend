package com.example.baygo.db.dto.response;

import java.util.ArrayList;
import java.util.List;

public record ColorResponse(
        String hexCode,
        String name
) {
    public static List<ColorResponse> getColors() {
        List<ColorResponse> colors = new ArrayList<>();
        colors.add(new ColorResponse("#FFFFFF", "белый"));
        colors.add(new ColorResponse("#FFBEBE", "Теплый розовый"));
        colors.add(new ColorResponse("#FFEBBE", "Светло-персиковый"));
        colors.add(new ColorResponse("#FFD37F", "Золотисто-персиковый"));
        colors.add(new ColorResponse("#FFFFBE", "Светло-желтый"));
        colors.add(new ColorResponse("#D1FF73", "Салатовый"));
        colors.add(new ColorResponse("#BEFFE8", "Бирюзовый"));
        colors.add(new ColorResponse("#BEE8FF", "Светло-голубой"));
        colors.add(new ColorResponse("#BED2FF", "Серебристо-голубой"));
        colors.add(new ColorResponse("#FFBEE8", "Розовато-лавандовый"));
        colors.add(new ColorResponse("#CD6666", "Темно-красный"));
        colors.add(new ColorResponse("#FFA77F", "Персиковый"));
        colors.add(new ColorResponse("#FFAA00", "Оранжевый"));
        colors.add(new ColorResponse("#72FFE0", "Аква"));
        colors.add(new ColorResponse("#73DFFF", "Голубой"));
        colors.add(new ColorResponse("#C500FF", "Фиолетовый"));
        colors.add(new ColorResponse("#CCCCCC", "Серый"));
        colors.add(new ColorResponse("#FF0000", "Красный"));
        colors.add(new ColorResponse("#E69800", "Оранжево-коричневый"));
        colors.add(new ColorResponse("#00C5FF", "Ярко-голубой"));
        colors.add(new ColorResponse("#040097", "Темно-синий"));
        colors.add(new ColorResponse("#FF00C5", "Розовый"));
        colors.add(new ColorResponse("#A80000", "Марсала"));
        colors.add(new ColorResponse("#4CE600", "Ярко-зеленый"));
        colors.add(new ColorResponse("#E8BEFF", "Фиолетово-розовый"));
        colors.add(new ColorResponse("#E1E1E1", "Светло-серый"));
        colors.add(new ColorResponse("#FFFF72", "Ярко-желтый"));
        colors.add(new ColorResponse("#D1FF74", "Светло-зеленый"));
        colors.add(new ColorResponse("#E6E600", "Лимонный"));
        colors.add(new ColorResponse("#AAFF00", "Лаймовый"));
        colors.add(new ColorResponse("#0071FF", "Ярко-синий"));
        colors.add(new ColorResponse("#E60000", "Ярко-красный"));
        colors.add(new ColorResponse("#A87000", "Темно-оранжевый"));
        colors.add(new ColorResponse("#A8A800", "Зеленовато-желтый"));
        colors.add(new ColorResponse("#8400A8", "Темно-фиолетовый"));
        colors.add(new ColorResponse("#A80084", "Темно-малиновый"));
        colors.add(new ColorResponse("#732600", "Темно-коричневый"));
        colors.add(new ColorResponse("#737300", "Темно-желтый"));
        colors.add(new ColorResponse("#38A800", "Темно-зеленый"));
        colors.add(new ColorResponse("#00A884", "Зелено-голубой"));
        colors.add(new ColorResponse("#0084A8", "Темно-голубой"));
        colors.add(new ColorResponse("#004DA8", "Сине-голубой"));
        colors.add(new ColorResponse("#0A29C0", "Темно-синий"));
        colors.add(new ColorResponse("#4C0073", "Пурпурно-синий"));
        colors.add(new ColorResponse("#73004C", "Пурпурно-розовый"));
        colors.add(new ColorResponse("#730000", "Мариновый"));
        colors.add(new ColorResponse("#D7B09E", "Красно-оранжевый"));
        colors.add(new ColorResponse("#D7C29E", "Коричневый"));
        colors.add(new ColorResponse("#267300", "Зеленый"));
        colors.add(new ColorResponse("#002673", "Синий"));
        colors.add(new ColorResponse("#343434", "Темно-серый"));
        colors.add(new ColorResponse("#F57A7A", "Светло-красный"));
        colors.add(new ColorResponse("#CD8966", "Светло-оранжевый"));
        colors.add(new ColorResponse("#F5CA7A", "Светло-коричневый"));
        colors.add(new ColorResponse("#CDCD66", "Желто-зеленый"));
        colors.add(new ColorResponse("#A5F57A", "Светло-зеленый"));
        colors.add(new ColorResponse("#9EAAD7", "Светло-синий"));
        colors.add(new ColorResponse("#AA66CD", "Пурпурно-синий"));
        colors.add(new ColorResponse("#000000", "Черный"));
        colors.add(new ColorResponse("#894444", "Красный"));
        colors.add(new ColorResponse("#895A44", "Красно-оранжевый"));
        colors.add(new ColorResponse("#898944", "Желто-зеленый"));
        colors.add(new ColorResponse("#5C8944", "Зеленый"));
        colors.add(new ColorResponse("#6699CD", "Сине-голубой"));
        return colors;
    }
}
