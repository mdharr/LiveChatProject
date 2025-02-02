package com.mdharr.livechat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mdharr.livechat.dtos.MessageDTO;
import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.services.MessageService;
import com.mdharr.livechat.services.RoomService;
import com.mdharr.livechat.services.UserService;
import com.mdharr.livechat.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    private final Map<String, WebSocketSession> sessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        Map<String, String> params = parseQuery(query);
        String roomId = params.get("roomId");
        String token = params.get("token");
        if (token != null) {
            session.getAttributes().put("token", token);
        }
        sessionMap.put(roomId, session);
        System.out.println("WebSocket connected for room: " + roomId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String query = session.getUri().getQuery();
            Map<String, String> params = parseQuery(query);
            String roomId = params.get("roomId");

            Optional<Room> roomOpt = roomService.findById(Integer.parseInt(roomId));
            if (roomOpt.isPresent()) {
                Room room = roomOpt.get();
                Message msg = new Message();

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                Map<String, Object> payload = mapper.readValue(message.getPayload(), Map.class);
                String content = (String) payload.get("content");

                msg.setContent(content);
                msg.setTimestamp(LocalDateTime.now());
                msg.setRoom(room);

                String token = (String) session.getAttributes().get("token");
                if (token != null) {
                    String username = tokenUtil.getUsernameFromJWT(token);
                    User sender = userService.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    msg.setSender(sender);
                } else {
                    throw new RuntimeException("No token provided in WebSocket connection");
                }

                Message savedMessage = messageService.saveMessage(msg);

                MessageDTO messageDTO = new MessageDTO(
                        savedMessage.getId(),
                        savedMessage.getContent(),
                        savedMessage.getTimestamp(),
                        savedMessage.getSender().getUsername(),
                        savedMessage.getRoom().getName()
                );
                broadcastMessage(roomId, messageDTO);
            }
        } catch (Exception e) {
            System.err.println("Error in handleTextMessage: " + e.getMessage());
            try {
                session.sendMessage(new TextMessage("{\"error\":\"" + e.getMessage() + "\"}"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void broadcastMessage(String roomId, MessageDTO messageDTO) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonMessage = mapper.writeValueAsString(messageDTO);
        for (WebSocketSession userSession : sessionMap.values()) {
            if (userSession.isOpen()) {
                userSession.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}