package com.mdharr.livechat.repositories;

import com.mdharr.livechat.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByNameContaining(String keyword);
    Optional<Room> findById(int id);
}
