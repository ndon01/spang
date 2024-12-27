package com.spang.api.authentication.registration.services;

import com.spang.api.authentication.api.entity.AuthenticationProfile;
import com.spang.api.common.events.AuthenticationProfileRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisteredUserEventPublicationService {
    public final ApplicationEventPublisher eventPublisher;
    public void publish(AuthenticationProfile authenticationProfile) {
        eventPublisher.publishEvent(new AuthenticationProfileRegisteredEvent(authenticationProfile.getId()));
    }
}
