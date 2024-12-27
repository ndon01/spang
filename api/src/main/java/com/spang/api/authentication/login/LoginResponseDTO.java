package com.spang.api.authentication.login;

import com.spang.api.users.api.projections.UserProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private UserProjection user;
}
