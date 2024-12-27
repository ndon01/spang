package com.spang.api.common.security.authorization;

import lombok.Data;

@Data
public class BasePermission {
    private String name;
    private String description = "";
    private boolean enabledByDefault = false;
}
