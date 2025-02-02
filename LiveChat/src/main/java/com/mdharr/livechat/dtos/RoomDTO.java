package com.mdharr.livechat.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private int id;
    private String name;
    private String creatorUsername;
    private List<MessageDTO> messages;

    public RoomDTO(int id, String name, String creatorUsername, List<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.creatorUsername = creatorUsername;
        this.messages = messages;
    }
}

