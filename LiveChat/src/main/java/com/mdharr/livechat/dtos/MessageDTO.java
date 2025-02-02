package com.mdharr.livechat.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private int id;
    private String content;
    private LocalDateTime timestamp;
    private String senderUsername;
    private String roomName;

    public MessageDTO(int id, String content, LocalDateTime timestamp, String senderUsername, String roomName) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.senderUsername = senderUsername;
        this.roomName = roomName;
    }
}


