package com.example.baygo.db.dto.response;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String image;
    private String message;
    private LocalDate localDate;
    private String timezone;
    private Boolean isSeller;

}
