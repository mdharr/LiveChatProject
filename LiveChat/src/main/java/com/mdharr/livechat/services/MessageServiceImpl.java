package com.mdharr.livechat.services;

import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.repositories.MessageRepository;
import com.mdharr.livechat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByRoomId(int roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}
