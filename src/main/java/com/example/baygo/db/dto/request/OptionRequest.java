package com.example.baygo.db.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionRequest {
    private String title;
    private int optionOrder;
}
