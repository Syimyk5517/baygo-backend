package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.MessageRequest;
import com.example.baygo.db.dto.response.chat.ChatResponse;
import com.example.baygo.db.dto.response.chat.NewMessageResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Chat;
import com.example.baygo.db.model.Message;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.Role;
import com.example.baygo.repository.ChatRepository;
import com.example.baygo.repository.MessageRepository;
import com.example.baygo.repository.custom.CustomChatRepository;
import com.example.baygo.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {
    private final JwtService jwtService;
    private final CustomChatRepository customChatRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Override
    public SimpleResponse sendMessage(MessageRequest messageRequest) {
        User user = jwtService.getAuthenticate();
        Chat chat;
        if (messageRequest.chatId() == 0) {
            Seller seller = user.getSeller();
            chat = seller.getChat() != null ? seller.getChat() : new Chat();
            seller.setChat(chat);
            chat.setSeller(seller);
            chatRepository.save(chat);
        } else {
            chat = chatRepository.findById(messageRequest.chatId())
                    .orElseThrow(() -> new NotFoundException(String.format("Чат с идентификатором %s не найден!", messageRequest.chatId())));
            chat.setUser(user);
            user.addChat(chat);
        }

        Message message = new Message();
        message.setMessage(messageRequest.message());
        message.setImage(messageRequest.image());
        message.setChat(chat);
        message.setTime(LocalDateTime.now());
        message.setIsNewMessage(true);
        message.setIsSeller(messageRequest.chatId() == 0);

        chat.addMessage(message);
        chatRepository.save(chat);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Сообщения успешно отправлено!")
                .build();
    }

    @Override
    public List<NewMessageResponse> hasNewMessage() {
        return customChatRepository.hasNewMessage();
    }

    @Override
    public List<ChatResponse> findAll() {
        return customChatRepository.findAll();
    }

    @Override
    public ChatResponse findById(Long targetChatId) {
        return customChatRepository.findById(targetChatId);
    }

    @Override
    public SimpleResponse updateMessage(Long messageId, MessageRequest messageRequest) {
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new NotFoundException(String.format("Сообщения с идентификатором %s не найден!", messageId)));
        Chat chat = chatRepository.findById(messageRequest.chatId())
                .orElseThrow(() -> new NotFoundException(String.format("Чат с идентификатором %s не найден!", messageRequest.chatId())));
        if (chat.getMessages().contains(message)) {
            if (message.getIsSeller()) {
                throw new BadRequestException("Вы не можете изменить чужое сообщение");
            }
            message.setMessage(messageRequest.message());
            message.setImage(messageRequest.image());
            message.setTime(LocalDateTime.now());
            messageRepository.save(message);
        } else {
            throw new BadRequestException("Вы этом чате такой собщения нету");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Сообщения с идентификатором %s успешно изменен!", messageId)).build();
    }

    @Override
    public SimpleResponse deleteMessage(Long messageId) {
        User user = jwtService.getAuthenticate();
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new NotFoundException(String.format("Сообщения с идентификатором %s не найден!", messageId)));

        boolean canDelete = (user.getRole().equals(Role.SELLER) && message.getIsSeller()) ||
                (!user.getRole().equals(Role.SELLER) && !message.getIsSeller());

        if (canDelete) {
            messageRepository.delete(message);
        } else {
            throw new BadRequestException("Вы не можете удалить это сообщению!");
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Сообщение успешно удалено!").build();
    }

    @Override
    public SimpleResponse deleteChat(Long chatId) {
        User user = jwtService.getAuthenticate();
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                () -> new NotFoundException(String.format("Чат с идентификатором %s не найден!", chatId)));

        if (user.getRole().equals(Role.SELLER)) {
            Seller seller = user.getSeller();
            seller.setChat(null);
        } else {
            chat.setUser(null);
        }

        if (chat.getSeller() == null && chat.getUser() == null) {
            chatRepository.delete(chat);
        } else {
            chatRepository.save(chat);
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Чат с идентификатором %s успешно удален!", chatId)).build();
    }

}
