package com.wira.Chat.Application.Controller;

import com.wira.Chat.Application.Model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        System.out.println("Received message: " + chatMessage.getContent() + " from " + chatMessage.getSender());
        simpMessagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }
}
