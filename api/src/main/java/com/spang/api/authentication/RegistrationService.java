package com.spang.api.authentication;

import com.spang.api.authentication.api.entity.AuthenticationProfile;
import com.spang.api.authentication.api.entity.AuthenticationProfileRepository;
import com.spang.api.authentication.passwords.services.PlainTextPasswordToHashedPasswordService;
import com.spang.api.authentication.registration.RegistrationDTO;
import com.spang.api.authentication.registration.exceptions.InvalidRegistrationRequestException;
import com.spang.api.authentication.registration.services.RegisteredUserEventPublicationService;
import com.spang.api.common.util.passwords.PasswordValidatorUtil;
import com.spang.api.users.UserRepository;
import com.spang.api.users.UserService;
import com.spang.api.users.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserService userService;
    private final PlainTextPasswordToHashedPasswordService plainTextPasswordToHashedPasswordService;
    private final RegisteredUserEventPublicationService registeredUserEventPublicationService;
    private final AuthenticationProfileRepository authenticationProfileRepository;
    private final UserRepository userRepository;

    public void register(RegistrationDTO registrationDTO) {
        boolean isRegistrationValid = true;
        InvalidRegistrationRequestException invalidRegistrationRequestException = new InvalidRegistrationRequestException("Invalid Registration Response from the Client");

        if (!PasswordValidatorUtil.validate(registrationDTO.getPassword())) {
            isRegistrationValid = false;
            invalidRegistrationRequestException.addInvalidKey("password", "Password is invalid.");
        }

        User user = userRepository.findByUsername(registrationDTO.getUsername());
        if (user != null) {
            isRegistrationValid = false;
            invalidRegistrationRequestException.addInvalidKey("username ", "Username is already in use.");
        }


        AuthenticationProfile authenticationProfile = authenticationProfileRepository.getByUsername(registrationDTO.getUsername()).orElse(null);
        if (userService.doesUserExistByUsername(registrationDTO.getUsername()) || authenticationProfile != null) {
            isRegistrationValid = false;
            invalidRegistrationRequestException.addInvalidKey("username", "Username is already in use.");
        }

        // return if invalid
        if (!isRegistrationValid) {
            throw invalidRegistrationRequestException;
        }

        user = User.builder()
                .username(registrationDTO.getUsername())
                .build();
        userRepository.save(user);


        authenticationProfile = new AuthenticationProfile();
        authenticationProfile.setUsername(registrationDTO.getUsername());
        authenticationProfile.setPasswordHash(plainTextPasswordToHashedPasswordService.convert(registrationDTO.getPassword()));
        authenticationProfile.setUserId(user.getId());
        authenticationProfileRepository.saveAndFlush(authenticationProfile);

        user.setAuthenticationProfileId(authenticationProfile.getId());
        userRepository.save(user);
        log.info("User registered with username: {}", registrationDTO.getUsername());
    }
}


