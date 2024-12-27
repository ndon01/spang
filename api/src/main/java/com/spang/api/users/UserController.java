package com.spang.api.users;

import com.spang.api.common.interfaces.GenericConverter;
import com.spang.api.users.api.User;
import com.spang.api.users.api.projections.UserProjection;
import com.spang.api.common.security.currentUser.CurrentUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "User endpoints")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GenericConverter<User, UserProjection> userProjectionConverterService;
    @GetMapping
    public List<UserProjection> getUsersV1(@CurrentUser User user, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        if (page == null) {
            page = 0;
        }

        if (size == null) {
            size = 10;
        }

        return userRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(userProjectionConverterService::convert)
                .toList();
    }


    @GetMapping("/client")
    @Transactional
    public UserProjection getClient(@CurrentUser User user) {
        return userProjectionConverterService.convert(user);
    }

    @GetMapping("/{userId}")
    public User getUserByIdV1(@CurrentUser User user, @PathVariable int userId) {
        log.info("Fetching user with ID: {}", userId);
        return userService.getUserById(userId)
                .orElse(null);  // Handle null case appropriately
    }
    @GetMapping("/batch")
    public List<User> getUsersByIds(@RequestParam List<Integer> userIds) {
        log.info("Fetching users with IDs: {}", userIds);
        return userService.getUsersByIds(userIds)
                .stream()
                .toList();
    }
}
