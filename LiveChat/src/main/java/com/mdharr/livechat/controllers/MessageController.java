package com.mdharr.livechat.controllers;

import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Message>> getMessagesByRoom(@PathVariable int roomId) {
        List<Message> messages = messageService.getMessagesByRoomId(roomId);
        return ResponseEntity.ok(messages);
    }
}
