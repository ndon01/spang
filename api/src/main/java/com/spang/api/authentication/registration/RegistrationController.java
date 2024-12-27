package com.spang.api.authentication.registration;

import com.spang.api.authentication.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/register")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationProjection> RegisterUserV1(@RequestBody RegistrationDTO registrationDTO) {
        registrationService.register(registrationDTO);
        return ResponseEntity.status(201).build();
    }
}

