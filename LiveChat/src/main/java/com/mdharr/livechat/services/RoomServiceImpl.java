package com.mdharr.livechat.services;

import com.mdharr.livechat.dtos.MessageDTO;
import com.mdharr.livechat.dtos.RoomDTO;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.exceptions.RoomNotFoundException;
import com.mdharr.livechat.repositories.RoomRepository;
import com.mdharr.livechat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(int id, Room room) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        existing.setName(room.getName());
        return roomRepository.save(existing);
    }

    @Override
    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> searchRooms(String keyword) {
        return roomRepository.findByNameContaining(keyword);
    }

    @Override
    public Optional<Room> findById(int id) {
        return Optional.ofNullable(roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id)));
    }

    @Override
    public void joinRoom(User user, Room room) {
        if (!room.getMembers().contains(user)) {
            room.getMembers().add(user);
            user.getRooms().add(room);
            userRepository.save(user);
            roomRepository.save(room);
        }
    }

    @Override
    public RoomDTO getRoomWithMessages(int roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        List<MessageDTO> messageDTOs = room.getMessages().stream()
                .map(msg -> new MessageDTO(msg.getId(), msg.getContent(), msg.getTimestamp(), msg.getSender().getUsername(), room.getName()))
                .collect(Collectors.toList());

        return new RoomDTO(room.getId(), room.getName(), room.getCreator().getUsername(), messageDTOs);
    }


    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO convertToDto(Room room) {
        String creatorUsername = room.getCreator() != null ? room.getCreator().getUsername() : null;

        List<MessageDTO> messageDTOs = room.getMessages().stream()
                .map(msg -> new MessageDTO(msg.getId(), msg.getContent(), msg.getTimestamp(), msg.getSender().getUsername(), msg.getRoom().getName()))
                .collect(Collectors.toList());

        return new RoomDTO(room.getId(), room.getName(), creatorUsername, messageDTOs);
    }
}
