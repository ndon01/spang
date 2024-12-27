package com.spang.api.common.security.authorization.requiresPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.METHOD, ElementType.TYPE}) // Can be applied to methods or classes
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    String value(); // The role required to access the method
    String[] values() default {};
}
