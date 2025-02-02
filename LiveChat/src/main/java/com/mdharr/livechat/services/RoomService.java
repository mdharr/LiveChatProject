package com.mdharr.livechat.services;

import com.mdharr.livechat.dtos.RoomDTO;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room createRoom(Room room);
    Room updateRoom(int id, Room room);
    void deleteRoom(int id);
    List<Room> searchRooms(String keyword);
    Optional<Room> findById(int id);
    void joinRoom(User user, Room room);
    RoomDTO getRoomWithMessages(int roomId);
    List<RoomDTO> getAllRooms();
    RoomDTO convertToDto(Room room);
}
