package com.mdharr.livechat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message msg = objectMapper.readValue(message.getPayload(), Message.class);
        msg.setTimestamp(LocalDateTime.now());
        Message saved = messageService.saveMessage(msg);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(saved)));
    }
}
