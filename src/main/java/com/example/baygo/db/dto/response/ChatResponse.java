package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.Message;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse{
    private Long id;
    private String fullName;
   private List<MessageResponse> messages;

}
