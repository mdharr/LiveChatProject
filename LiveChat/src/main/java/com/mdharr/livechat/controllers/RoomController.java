package com.mdharr.livechat.controllers;

import com.mdharr.livechat.dtos.RoomDTO;
import com.mdharr.livechat.dtos.UserDTO;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room created = roomService.createRoom(room);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable int id, @RequestBody Room room) {
        Room updated = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRooms(@RequestParam String keyword) {
        List<Room> rooms = roomService.searchRooms(keyword);
        return ResponseEntity.ok(rooms);
    }

    private RoomDTO convertToDto(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoomName(room.getName());

        User creator = room.getUser();
        if (creator != null) {
            UserDTO userDto = new UserDTO();
            userDto.setId(creator.getId());
            userDto.setUsername(creator.getUsername());
            dto.setCreator(userDto);
        }
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDTOs);
    }
}
