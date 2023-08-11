package com.example.baygo.repository.custom.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.ChatResponse;
import com.example.baygo.db.dto.response.MessageResponse;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.Role;
import com.example.baygo.repository.custom.CustomChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomChatRepositoryImpl implements CustomChatRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;

    @Override
    public List<ChatResponse> findAll() {
        User user = jwtService.getAuthenticate();
        String chatSql = """
                SELECT c.id AS chatId,
                       CONCAT(s.first_name, ' ', s.last_name) AS fullName
                FROM chats c
                LEFT JOIN sellers s ON c.id = s.chat_id
                WHERE c.user_id = ? OR  (s.id = ?)
                """;

        return jdbcTemplate.query(chatSql, (chatResultSet) -> {
            List<ChatResponse> responses = new ArrayList<>();
            while (chatResultSet.next()) {
                ChatResponse chatResponse = new ChatResponse();
                chatResponse.setId(chatResultSet.getLong("chatId"));
                List<MessageResponse> messages = retrieveMessages(chatResponse.getId());
                chatResponse.setMessages(messages);
                String fullName = user.getRole().equals(Role.SELLER) ? "Admin BuyGo" : chatResultSet.getString("fullName");
                chatResponse.setFullName(fullName);

                responses.add(chatResponse);
            }
            return responses;
        }, user.getId(), (user.getSeller() != null) ? user.getSeller().getId() : null);
    }

    private List<MessageResponse> retrieveMessages(Long chatId) {
        String messageSql = """
                SELECT
                    m.message as message,
                    m.id as messageId,
                    m.image as image,
                    m.is_seller as isSeller,
                    CAST(m.time AS DATE) as extractedDate,
                    EXTRACT(HOUR FROM m.time) as hour,
                    EXTRACT(MINUTE FROM m.time) as minute
                FROM messages m
                WHERE m.chat_id = ?
                ORDER BY m.time
                """;

        return jdbcTemplate.query(messageSql, (messageResultSet) -> {
            List<MessageResponse> messages = new ArrayList<>();
            while (messageResultSet.next()) {
                int hour = messageResultSet.getInt("hour");
                int minute = messageResultSet.getInt("minute");
                MessageResponse messageResponse = MessageResponse.builder()
                        .id(messageResultSet.getLong("messageId"))
                        .message(messageResultSet.getString("message"))
                        .image(messageResultSet.getString("image"))
                        .isSeller(messageResultSet.getBoolean("isSeller"))
                        .localDate(messageResultSet.getDate("extractedDate").toLocalDate())
                        .timezone(hour + ":" + minute)
                        .build();
                messages.add(messageResponse);
            }
            return messages;
        }, chatId);

    }

}
