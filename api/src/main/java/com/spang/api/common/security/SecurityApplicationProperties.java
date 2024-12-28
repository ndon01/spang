package com.spang.api.common.security;

import com.spang.api.common.security.jwt.JwtTokenSettings;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app.security")
@Data
public class SecurityApplicationProperties {
    private JwtTokenSettings accessToken;
}
