package com.mdharr.livechat.services;

import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.repositories.RoomRepository;
import com.mdharr.livechat.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(int id, Room room) {
        Room existing = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
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
}
