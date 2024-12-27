package com.spang.api.users;

import com.spang.api.users.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateService {
    private  final UserRepository userRepository;
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
