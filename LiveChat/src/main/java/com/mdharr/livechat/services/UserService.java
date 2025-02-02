package com.mdharr.livechat.services;

import com.mdharr.livechat.entities.User;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
}
