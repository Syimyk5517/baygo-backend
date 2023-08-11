package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.CalendarActionResponse;
import com.example.baygo.db.dto.response.DayPromotion;
import com.example.baygo.repository.custom.CustomCalendarActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomCalendarActionRepositoryImpl implements CustomCalendarActionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CalendarActionResponse> getAllDiscount(LocalDate date) {
        List<CalendarActionResponse> calendarActionResponses = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            LocalDate currentDate = (date != null) ? date : LocalDate.now();
            LocalDate futureDate = currentDate.plusDays(i);
            CalendarActionResponse calendarActionResponse = CalendarActionResponse.builder()
                    .date(futureDate)
                    .build();

            String sql = """
                    SELECT EXTRACT(HOUR FROM date_of_start::timestamp) AS hour,date_of_finish
                    FROM discounts d
                    WHERE EXTRACT(YEAR FROM date_of_start::timestamp) = ?
                      AND EXTRACT(MONTH FROM date_of_start::timestamp) = ?
                      AND EXTRACT(DAY FROM date_of_start::timestamp) = ?
                      AND EXTRACT(HOUR FROM date_of_start::timestamp) > 7 AND EXTRACT(HOUR FROM date_of_start::timestamp) < 17 ;
                    """;

            List<DayPromotion> dayPromotions = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                return DayPromotion.builder()
                        .time(resultSet.getInt("hour"))
                        .dateOfFinish(resultSet.getDate("date_of_finish").toLocalDate())
                        .build();
            }, futureDate.getYear(), futureDate.getMonthValue(), futureDate.getDayOfMonth());
            calendarActionResponse.setDayPromotions(dayPromotions);
            calendarActionResponses.add(calendarActionResponse);
        }

        return calendarActionResponses;

    }

}