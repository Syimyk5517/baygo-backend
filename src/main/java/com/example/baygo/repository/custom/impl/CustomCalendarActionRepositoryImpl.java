package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.CalendarActionResponse;
import com.example.baygo.repository.custom.CustomCalendarActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomCalendarActionRepositoryImpl implements CustomCalendarActionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CalendarActionResponse> getAllDiscount(LocalDate date) {

        LocalDate startDate;
        LocalDate endDate;

        if (date != null) {
            startDate = date.withDayOfMonth(1);
            endDate = date.withDayOfMonth(startDate.lengthOfMonth());
            System.out.println(endDate);
        } else {
            startDate = LocalDate.now().withDayOfMonth(1);
            endDate = LocalDate.now().withDayOfMonth(startDate.lengthOfMonth());
            System.out.println("now" + endDate);
        }


        String sql = """
                SELECT date_of_start::date, EXTRACT(HOUR FROM date_of_start::timestamp) AS hour, date_of_finish
                FROM discounts d
                WHERE date_of_start >= ?::date
                  AND date_of_start <= ?::date
                  AND EXTRACT(HOUR FROM date_of_start::timestamp) > 8
                  AND EXTRACT(HOUR FROM date_of_start::timestamp) < 17 
                  ORDER BY date_of_start
                """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return CalendarActionResponse.builder()
                    .dateOfStart(resultSet.getDate("date_of_start").toLocalDate())
                    .time(resultSet.getInt("hour"))
                    .dateOfFinish(resultSet.getDate("date_of_finish").toLocalDate())
                    .build();
        }, startDate, endDate);


    }


}