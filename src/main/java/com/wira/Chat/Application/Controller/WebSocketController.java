package com.wira.Chat.Application.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.wira.Chat.Application.Model.ChatMessage;

@Controller
public class WebSocketController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        return new ChatMessage(message.getContent(), message.getSender(), message.getRecipient());
    }
}
