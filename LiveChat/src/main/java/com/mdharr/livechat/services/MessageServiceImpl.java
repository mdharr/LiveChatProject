package com.mdharr.livechat.services;

import com.mdharr.livechat.dtos.MessageDTO;
import com.mdharr.livechat.entities.Message;
import com.mdharr.livechat.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageDTO> getMessagesByRoomId(int roomId) {
        List<Message> messages = messageRepository.findByRoomId(roomId);
        return messages.stream()
                .map(msg -> new MessageDTO(msg.getId(), msg.getContent(), msg.getTimestamp(), msg.getSender().getUsername(), msg.getRoom().getName()))
                .collect(Collectors.toList());
    }
}
