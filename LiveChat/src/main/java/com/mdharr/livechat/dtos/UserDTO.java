package com.mdharr.livechat.dtos;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;

    public UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
