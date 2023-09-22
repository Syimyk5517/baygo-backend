package com.example.baygo.repository.custom.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.chat.ChatResponse;
import com.example.baygo.db.dto.response.chat.MessageResponse;
import com.example.baygo.db.dto.response.chat.NewMessageResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Chat;
import com.example.baygo.db.model.Message;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.Role;
import com.example.baygo.repository.ChatRepository;
import com.example.baygo.repository.MessageRepository;
import com.example.baygo.repository.custom.CustomChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomChatRepositoryImpl implements CustomChatRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Override
    public List<ChatResponse> findAll() {
        User user = jwtService.getAuthenticate();
        System.out.println(user.getId());
        String chatSql = """
                SELECT c.id AS chatId,
                       CONCAT(s.first_name, ' ', s.last_name) AS fullName
                FROM chats c
                LEFT JOIN sellers s ON c.id = s.chat_id
                WHERE c.user_id = ? OR  (s.id = ?)
                """;

        return jdbcTemplate.query(chatSql, (chatResultSet, i) -> {
            ChatResponse chatResponse = new ChatResponse();
            String fullName = user.getRole().equals(Role.SELLER) ? "Admin BuyGo" : chatResultSet.getString("fullName");
            chatResponse.setId(chatResultSet.getLong("chatId"));
            chatResponse.setFullName(fullName);
            List<MessageResponse> messageResponses = jdbcTemplate.query(messageSql(), (messageResultSet, j) -> {
                int hour = messageResultSet.getInt("hour");
                int minute = messageResultSet.getInt("minute");
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setId(messageResultSet.getLong("messageId"));
                messageResponse.setMessage(messageResultSet.getString("message"));
                messageResponse.setImage(messageResultSet.getString("image"));
                messageResponse.setIsSeller(messageResultSet.getBoolean("isSeller"));
                messageResponse.setLocalDate(messageResultSet.getDate("extractedDate").toLocalDate());
                messageResponse.setTimezone(hour + ":" + minute);
                return messageResponse;
            }, chatResponse.getId());
            chatResponse.setMessages(messageResponses);
            return chatResponse;
        }, user.getId(), (user.getSeller() != null) ? user.getSeller().getId() : null);

    }

    @Override
    public ChatResponse findById(Long targetChatId) {
        User user = jwtService.getAuthenticate();
        Chat chat = chatRepository.findById(targetChatId).orElseThrow(
                () -> new NotFoundException(String.format("Чат с идентификатором %s не найден.", targetChatId)));
        String singleChatSql = """
                SELECT c.id AS chatId,
                       CONCAT(s.first_name, ' ', s.last_name) AS fullName
                FROM chats c
                LEFT JOIN sellers s ON c.id = s.chat_id
                WHERE c.id = ?
                """;
        return jdbcTemplate.queryForObject(singleChatSql, (chatResultSet, rowNum) -> {
            ChatResponse chatResponse = new ChatResponse();
            chatResponse.setId(chatResultSet.getLong("chatId"));
            String fullName = user.getRole().equals(Role.SELLER) ? "Admin BuyGo" : chatResultSet.getString("fullName");
            chatResponse.setFullName(fullName);
            List<MessageResponse> messageResponses = jdbcTemplate.query(messageSql(), (messageResultSet, j) -> {
                int hour = messageResultSet.getInt("hour");
                int minute = messageResultSet.getInt("minute");
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setId(messageResultSet.getLong("messageId"));
                messageResponse.setMessage(messageResultSet.getString("message"));
                messageResponse.setImage(messageResultSet.getString("image"));
                messageResponse.setIsSeller(messageResultSet.getBoolean("isSeller"));
                messageResponse.setLocalDate(messageResultSet.getDate("extractedDate").toLocalDate());
                messageResponse.setTimezone(hour + ":" + minute);
                Message message = messageRepository.findById(messageResponse.getId())
                        .orElseThrow(() -> new NotFoundException("Message with supplyId " + messageResponse.getId() + " not found"));

                boolean isNewMessage = message.getIsNewMessage();
                boolean shouldUpdate = (user.getRole() == Role.SELLER && (message.getIsSeller() || isNewMessage))
                        || (user.getRole() != Role.SELLER && (!message.getIsSeller() || isNewMessage));

                if (shouldUpdate) {
                    message.setIsNewMessage(false);
                    messageRepository.save(message);
                }
                return messageResponse;
            }, chatResponse.getId());
            chatResponse.setMessages(messageResponses);
            return chatResponse;
        }, chat.getId());

    }


    @Override
    public List<NewMessageResponse> hasNewMessage() {
        User user = jwtService.getAuthenticate();
        String role = user.getRole().toString();
        String fullNameCalculation = (role.equals("SELLER")) ? "'Admin BuyGo'" : "CONCAT(s.first_name, ' ', s.last_name)";
        String sql =
                "SELECT " +
                        "m.is_new_message as newMessage, " +
                        "c.id as chatId, " +
                        fullNameCalculation + " AS fullName, " +
                        "count(m) as countMessage " +
                        "FROM chats c " +
                        "JOIN sellers s ON c.id = s.chat_id " +
                        "JOIN messages m ON c.id = m.chat_id " +
                        "WHERE m.is_seller = " + (role.equals("SELLER") ? "false " : "true ") +
                        "AND m.is_new_message = true " +
                        " GROUP BY m.is_new_message, c.id, s.first_name, s.last_name";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return NewMessageResponse.builder()
                    .isNewMessage(resultSet.getBoolean("newMessage"))
                    .chatId(resultSet.getLong("chatId"))
                    .fullName(resultSet.getString("fullName"))
                    .countNewMessage(resultSet.getInt("countMessage"))
                    .build();
        });
    }

    private String messageSql() {
        return """
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

    }
}



