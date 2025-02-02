package com.mdharr.livechat.controllers;

import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.services.RoomService;
import com.mdharr.livechat.services.UserService;
import com.mdharr.livechat.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RoomControllerUnitTest {

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil tokenUtil;

    @InjectMocks
    private RoomController roomController;

    private User sampleUser;
    private Room sampleRoom;

    @BeforeEach
    public void setup() {
        sampleUser = new User();
        sampleUser.setId(1);
        sampleUser.setUsername("testuser");

        sampleRoom = new Room();
        sampleRoom.setId(100);
        sampleRoom.setName("Test Room");
        sampleRoom.setCreator(sampleUser);
    }

    @Test
    public void testJoinRoomSuccess() {
        String tokenHeader = "Bearer validToken";

        given(tokenUtil.getUsernameFromJWT("validToken")).willReturn("testuser");
        given(userService.findByUsername(anyString())).willReturn(Optional.of(sampleUser));
        given(roomService.findById(anyInt())).willReturn(Optional.of(sampleRoom));

        ResponseEntity<String> response = roomController.joinRoom(100, tokenHeader);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User joined the room successfully", response.getBody());
    }

    @Test
    public void testJoinRoomNotFound() {
        String tokenHeader = "Bearer validToken";
        given(tokenUtil.getUsernameFromJWT("validToken")).willReturn("testuser");
        given(userService.findByUsername(anyString())).willReturn(Optional.of(sampleUser));
        given(roomService.findById(anyInt())).willReturn(Optional.empty());

        ResponseEntity<String> response = roomController.joinRoom(100, tokenHeader);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Room not found", response.getBody());
    }
}
