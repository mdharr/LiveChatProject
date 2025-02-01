package com.mdharr.livechat.dtos;

import lombok.Data;

@Data
public class RoomDTO {
    private int id;
    private String roomName;
    private UserDTO creator;
}
