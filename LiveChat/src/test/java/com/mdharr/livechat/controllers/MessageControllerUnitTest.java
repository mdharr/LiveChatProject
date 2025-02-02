package com.mdharr.livechat.controllers;

import com.mdharr.livechat.dtos.MessageDTO;
import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.security.JwtTokenUtil;
import com.mdharr.livechat.services.MessageService;
import com.mdharr.livechat.services.RoomService;
import com.mdharr.livechat.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MessageControllerUnitTest {

    @Mock
    private MessageService messageService;

    @Mock
    private UserService userService;

    @Mock
    private RoomService roomService;

    @Mock
    private JwtTokenUtil tokenUtil;

    @InjectMocks
    private MessageController messageController;

    private User sampleUser;
    private Room sampleRoom;
    private String tokenHeader;

    @BeforeEach
    public void setup() {
        // Create a sample user and room for testing
        sampleUser = new User();
        sampleUser.setId(1);
        sampleUser.setUsername("testuser");

        sampleRoom = new Room();
        sampleRoom.setId(100);
        sampleRoom.setName("Test Room");
        sampleRoom.setCreator(sampleUser);

        // The token header contains "Bearer " followed by the token string.
        tokenHeader = "Bearer validToken";
    }

    @Test
    public void testSendMessageSuccess() {
        when(tokenUtil.getUsernameFromJWT("validToken")).thenReturn("testuser");
        when(userService.findByUsername(anyString())).thenReturn(Optional.of(sampleUser));
        when(roomService.findById(anyInt())).thenReturn(Optional.of(sampleRoom));

        Message expectedMessage = new Message();
        expectedMessage.setId(0);
        expectedMessage.setContent("Hello World!");
        LocalDateTime fixedTimestamp = LocalDateTime.now();
        expectedMessage.setTimestamp(fixedTimestamp);
        expectedMessage.setSender(sampleUser);
        expectedMessage.setRoom(sampleRoom);

        when(messageService.saveMessage(any(Message.class)))
                .thenReturn(expectedMessage);

        ResponseEntity<MessageDTO> response = messageController.sendMessage(100, tokenHeader, "Hello World!");

        MessageDTO actualMessage = response.getBody();
        assertEquals(200, response.getStatusCodeValue());

        assertEquals(expectedMessage.getId(), actualMessage.getId(), "ID should match");
        assertEquals(expectedMessage.getContent(), actualMessage.getContent(), "Content should match");
        assertEquals(expectedMessage.getSender().getUsername(), actualMessage.getSenderUsername(), "Sender username should match");
        assertEquals(expectedMessage.getRoom().getName(), actualMessage.getRoomName(), "Room name should match");
    }

    @Test
    public void testSendMessageRoomNotFound() {
        when(tokenUtil.getUsernameFromJWT("validToken")).thenReturn("testuser");

        when(userService.findByUsername(anyString())).thenReturn(Optional.of(sampleUser));

        when(roomService.findById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<MessageDTO> response = messageController.sendMessage(100, tokenHeader, "Hello World!");

        assertEquals(404, response.getStatusCodeValue());
    }
}

