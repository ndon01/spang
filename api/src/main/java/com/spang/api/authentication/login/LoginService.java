package com.spang.api.authentication.login;

import com.spang.api.authentication.api.entity.AuthenticationProfile;
import com.spang.api.authentication.api.entity.AuthenticationProfileRepository;
import com.spang.api.authentication.passwords.services.PlainTextAndHashedPasswordMatchingService;
import com.spang.api.authentication.tokens.AuthenticationProfileToAccessTokenConverterService;
import com.spang.api.users.UserRepository;
import com.spang.api.users.UserSearchService;
import com.spang.api.users.api.User;
import com.spang.api.users.api.projections.converters.UserProjectionConverter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationProfileRepository authenticationProfileRepository;
    private final PlainTextAndHashedPasswordMatchingService plainTextAndHashedPasswordMatchingService;
    private final AuthenticationProfileToAccessTokenConverterService authenticationProfileToAccessTokenConverterService;
    private final UserProjectionConverter userProjectionConverter;
    private final UserSearchService userSearchService;
    private final UserRepository userRepository;

    public AuthenticationProfile loginForAuthenticationProfile(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        AuthenticationProfile authenticationProfile = authenticationProfileRepository.findById(user.getAuthenticationProfileId()).orElse(null);
        if (authenticationProfile == null) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        if (!plainTextAndHashedPasswordMatchingService.match(password, authenticationProfile.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        return authenticationProfile;
    }

    public String loginForToken(String username, String password) {
        AuthenticationProfile authenticationProfile = loginForAuthenticationProfile(username, password);
        return authenticationProfileToAccessTokenConverterService.convert(authenticationProfile);
    }

    private static final int expirationInSeconds = 60 * 60 * 24 * 7;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        AuthenticationProfile authenticationProfile = loginForAuthenticationProfile(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        String token = authenticationProfileToAccessTokenConverterService.convert(authenticationProfile);
        User user = userSearchService.getUserByUserId(authenticationProfile.getUserId());

        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            HttpServletResponse response = ra.getResponse();

            ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                    .httpOnly(true)
                    .maxAge(expirationInSeconds)
                    .path("/")
                    .sameSite("Strict")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }

        return LoginResponseDTO.builder()
                        .accessToken(token)
                        .user(userProjectionConverter.convert(user))
                        .build();
    }
}
