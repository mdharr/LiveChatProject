package com.mdharr.livechat.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private int id;
    private String content;
    private LocalDateTime timestamp;
    private UserDTO sender;
}
