package com.spang.api.admin.users.updateUser;

import com.spang.api.users.api.User;
import com.spang.api.users.api.projections.UserProjection;
import com.spang.api.users.UserSearchService;
import com.spang.api.users.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserSearchService userSearchService;
    private final UserUpdateService userUpdateService;
    public void updateUser(int userId, UserProjection userProjection) {
        User user = userSearchService.getUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }

        user.setUsername(userProjection.getUsername());
        user.setRoles(userProjection.getRoles());
        user.setPermissions(userProjection.getPermissions());

        userUpdateService.updateUser(user);
    }
}
