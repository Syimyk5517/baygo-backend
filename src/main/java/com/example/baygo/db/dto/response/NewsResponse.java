
package com.example.baygo.db.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String topic;
    private String description;
    private LocalDate date;

}

