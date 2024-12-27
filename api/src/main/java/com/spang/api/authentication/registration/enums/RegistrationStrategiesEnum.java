package com.spang.api.authentication.registration.enums;

public enum RegistrationStrategiesEnum {
    email("email");

    private final String strategy;

    RegistrationStrategiesEnum(String strategy) {
        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }
}
