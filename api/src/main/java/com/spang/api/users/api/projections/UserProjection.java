package com.spang.api.users.api.projections;

import com.spang.api.authorization.Permission;
import com.spang.api.authorization.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserProjection {
    private int id;
    private String username;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
