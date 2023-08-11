package com.example.baygo.db.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CalendarActionResponse {

    private LocalDate date;
    private List<DayPromotion> dayPromotions;

    public void addDayPromotion(DayPromotion dayPromotion) {
        if (this.dayPromotions == null) {
            this.dayPromotions = new ArrayList<>();
        }
        this.dayPromotions.add(dayPromotion);
    }

}
