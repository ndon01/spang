package com.spang.api.common.security.jwt;

import lombok.Data;

@Data
public class JwtTokenSettings {
    private String secret;
    private long expirationTime;
}
