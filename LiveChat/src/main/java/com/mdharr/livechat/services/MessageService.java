package com.mdharr.livechat.services;

import com.mdharr.livechat.dtos.MessageDTO;
import com.mdharr.livechat.entities.Message;
import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);
    List<MessageDTO> getMessagesByRoomId(int roomId);
}
