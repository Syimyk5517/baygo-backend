package com.example.baygo.api;

import com.example.baygo.db.dto.request.MessageRequest;
import com.example.baygo.db.dto.response.chat.ChatResponse;
import com.example.baygo.db.dto.response.chat.NewMessageResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {
    private final ChatService chatService;

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @Operation(summary = "Get all chats", description = "This is the method to return all chats")
    @GetMapping
    public List<ChatResponse> findAll() {
        return chatService.findAll();
    }
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @GetMapping("/find_by_id")
    public ChatResponse findById(Long targetChatId){
        return chatService.findById(targetChatId);
    }
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @GetMapping("/new_message")
    List<NewMessageResponse> hasNewMessage(){
        return chatService.hasNewMessage();
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @Operation(summary = "Send message", description = "This method send a message")
    @PostMapping("/send_message")
    public SimpleResponse sendMessage(@RequestBody MessageRequest messageRequest) {
        return chatService.sendMessage(messageRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update message ", description = "This is the method to update the message")
    @PutMapping("/update")
    public SimpleResponse updateMessage(@RequestParam Long messageId, @RequestBody MessageRequest messageRequest) {
        return chatService.updateMessage(messageId, messageRequest);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @Operation(summary = "Delete chat", description = "This is the method to delete the chat")
    @DeleteMapping("/delete_chat")
    public SimpleResponse deleteChat(Long chatId) {
        return chatService.deleteChat(chatId);
    }

    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @Operation(summary = "Delete message", description = "This is the method to delete the message")
    @DeleteMapping("/delete_message")
    public SimpleResponse deleteMessage(Long messageId) {
        return chatService.deleteMessage(messageId);
    }
}