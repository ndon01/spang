package com.spang.api.authentication.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "clms.security.oauth2.google")
public class OAuthConfiguration {
    private boolean enabled = false;
    private String clientId;
    private String clientSecret; // Add if needed
    private String redirectUri;
}
