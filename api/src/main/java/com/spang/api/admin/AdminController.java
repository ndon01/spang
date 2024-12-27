package com.spang.api.admin;

import com.spang.api.admin.users.updateUser.AdminUserService;
import com.spang.api.authentication.AuthenticationService;
import com.spang.api.authentication.RegistrationService;
import com.spang.api.authorization.RoleCRUDService;
import com.spang.api.authorization.RoleUpdateService;
import com.spang.api.authorization.Role;
import com.spang.api.common.interfaces.GenericConverter;
import com.spang.api.common.security.authorization.requiresPermission.RequiresPermission;
import com.spang.api.users.api.User;
import com.spang.api.users.api.projections.UserProjection;
import com.spang.api.users.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@RequiresPermission("ADMIN_PAGE_ACCESS")
@Tag(name = "Admin", description = "Admin endpoints for managing users and roles")
public class AdminController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;
    private final AdminUserService adminUserService;
    private final RoleUpdateService roleUpdateService;
    private final RoleCRUDService roleCRUDService;
    private final GenericConverter<User, UserProjection> userProjectionConverterService;

    @GetMapping("/users")
    public List<UserProjection> getUsers() {
        return userService.getUsers().stream().map(userProjectionConverterService::convert).toList();
    }
    @PostMapping("/users/createUser")
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody NewUserRequest newUserRequest){
        int id = userService.createUserWithUsername(newUserRequest.getUsername());
        authenticationService.createAuthenticationProfile(newUserRequest.getUsername(), newUserRequest.getPassword(), id);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/users/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int userId, @RequestBody UserProjection userProjection){
        adminUserService.updateUser(userId, userProjection);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/authorization/roles/createRole")
    public ResponseEntity<?> createRole(@RequestBody Role role){
        Role createdRole = new Role();
        createdRole.setName(role.getName());
        createdRole.setDescription(role.getDescription());
        createdRole.setPermissions(role.getPermissions());
        roleCRUDService.createRole(createdRole);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/authorization/roles/updateRole/{id}")
    public ResponseEntity<?> updateRole(@PathVariable("id") int roleId, @RequestBody Role role) {
        roleUpdateService.updateRole(roleId, role);
        return ResponseEntity.status(201).build();
    }


}


@Data
class NewUserRequest {
    private String username;
    private String password;
}
