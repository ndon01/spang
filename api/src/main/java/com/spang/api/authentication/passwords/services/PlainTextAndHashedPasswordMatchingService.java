package com.spang.api.authentication.passwords.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlainTextAndHashedPasswordMatchingService {
    private final PasswordHashingComponent passwordEncryptionComponent;
    public boolean match(String plainText, String hashedPassword) {
        return passwordEncryptionComponent.match(plainText, hashedPassword);
    }
}
