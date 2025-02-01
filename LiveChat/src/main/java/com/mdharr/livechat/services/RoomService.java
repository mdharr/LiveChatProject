package com.mdharr.livechat.services;

import com.mdharr.livechat.entities.Room;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    Room updateRoom(int id, Room room);
    void deleteRoom(int id);
    List<Room> searchRooms(String keyword);
}
