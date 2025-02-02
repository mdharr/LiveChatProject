package com.mdharr.livechat.controllers;

import com.mdharr.livechat.dtos.RoomDTO;
import com.mdharr.livechat.dtos.UserDTO;
import com.mdharr.livechat.entities.Room;
import com.mdharr.livechat.entities.User;
import com.mdharr.livechat.security.JwtTokenUtil;
import com.mdharr.livechat.services.RoomService;
import com.mdharr.livechat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable int roomId) {
        RoomDTO roomDTO = roomService.getRoomWithMessages(roomId);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody Room room, @RequestHeader("Authorization") String token) {
        String username = tokenUtil.getUsernameFromJWT(token.substring(7));

        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        room.setCreator(user);
        Room createdRoom = roomService.createRoom(room);

        RoomDTO roomDTO = roomService.convertToDto(createdRoom);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<String> joinRoom(@PathVariable int roomId, @RequestHeader("Authorization") String token) {
        String username = tokenUtil.getUsernameFromJWT(token.substring(7));

        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Room> room = roomService.findById(roomId);
        if (room.isPresent()) {
            roomService.joinRoom(user, room.get());
            return ResponseEntity.ok("User joined the room successfully");
        } else {
            return ResponseEntity.status(404).body("Room not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable int id, @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        RoomDTO roomDTO = roomService.convertToDto(updatedRoom);
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomDTO>> searchRooms(@RequestParam String keyword) {
        List<Room> rooms = roomService.searchRooms(keyword);
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(roomService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> roomDTOs = roomService.getAllRooms();
        return ResponseEntity.ok(roomDTOs);
    }
}

