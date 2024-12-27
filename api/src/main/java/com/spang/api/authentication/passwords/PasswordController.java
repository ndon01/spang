package com.spang.api.authentication.passwords;

import com.spang.api.authentication.passwords.dto.PasswordUpdateRequestDto;
import com.spang.api.authentication.passwords.services.PasswordUpdateService;
import com.spang.api.common.interfaces.GenericConverter;
import com.spang.api.common.security.currentUser.CurrentUser;
import com.spang.api.users.UserRepository;
import com.spang.api.users.api.User;
import com.spang.api.users.api.projections.UserProjection;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication/passwords")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class PasswordController
{
    private final PasswordUpdateService passwordUpdateService;
    private final UserRepository userRepository;
    private final GenericConverter<User, UserProjection> userProjectionConverterService;

    @PostMapping("/update")
    public ResponseEntity<?> updatePassword(@CurrentUser User user, @RequestBody PasswordUpdateRequestDto request) {

        try {
            passwordUpdateService.updatePassword(user, request.getCurrentPassword(), request.getNewPassword());
        }catch(Exception e) {
            log.error("Error updating password", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
