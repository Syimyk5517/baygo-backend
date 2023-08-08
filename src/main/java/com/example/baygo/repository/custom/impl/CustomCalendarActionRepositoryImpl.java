package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.CalendarActionResponse;
import com.example.baygo.db.dto.response.DayPromotion;
import com.example.baygo.repository.custom.CustomCalendarActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
            for (int j = 9; j <= 16; j++) {
                String sql = """
                        SELECT EXTRACT(HOUR FROM date_of_start::timestamp) AS hour,date_of_finish
                        FROM discounts d
                        WHERE EXTRACT(YEAR FROM date_of_start::timestamp) = ?
                          AND EXTRACT(MONTH FROM date_of_start::timestamp) = ?
                          AND EXTRACT(DAY FROM date_of_start::timestamp) = ?
                          AND EXTRACT(HOUR FROM date_of_start::timestamp) = ?;
                        """;

                DayPromotion dayPromotion = null;
                try {
                    dayPromotion = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> {
                        int hourInDatabase = resultSet.getInt("hour");
                        LocalDate dateOfFinish = resultSet.getDate("date_of_finish").toLocalDate();
                        return DayPromotion.builder()
                                .time(hourInDatabase)
                                .dateOfFinish(dateOfFinish)
                                .build();
                    }, futureDate.getYear(), futureDate.getMonthValue(), futureDate.getDayOfMonth(), j);
                } catch (EmptyResultDataAccessException ignored) {
                }
                if (dayPromotion != null) {
                    calendarActionResponse.addDayPromotion(dayPromotion);
                }
            }
            calendarActionResponses.add(calendarActionResponse);
        }
        return calendarActionResponses;

    }

}