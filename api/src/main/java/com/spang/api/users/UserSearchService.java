package com.spang.api.users;

import com.spang.api.users.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    public User getUserByUserId(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username).orElse(null);
    }
}
