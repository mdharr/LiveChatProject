package com.mdharr.livechat.controllers;

import com.mdharr.livechat.dtos.MessageDTO;
import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.security.JwtTokenUtil;
import com.mdharr.livechat.services.MessageService;
import com.mdharr.livechat.services.RoomService;
import com.mdharr.livechat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByRoom(@PathVariable int roomId) {
        List<MessageDTO> messages = messageService.getMessagesByRoomId(roomId);
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestParam int roomId, @RequestHeader("Authorization") String token, @RequestBody String content) {
        String username = tokenUtil.getUsernameFromJWT(token.substring(7));
        User sender = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Room> room = roomService.findById(roomId);
        if (room.isPresent()) {
            Message message = new Message();
            message.setContent(content);
            message.setTimestamp(LocalDateTime.now());
            message.setSender(sender);
            message.setRoom(room.get());

            Message savedMessage = messageService.saveMessage(message);

            MessageDTO messageDTO = new MessageDTO(savedMessage.getId(), savedMessage.getContent(), savedMessage.getTimestamp(),
                    savedMessage.getSender().getUsername(), savedMessage.getRoom().getName());

            return ResponseEntity.ok(messageDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

