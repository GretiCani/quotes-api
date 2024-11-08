package com.quotes.domain.entity;

import java.util.Arrays;

public enum Role {
    ADMIN,USER;

    public static Role fromValue(String value) {
        return Arrays.stream(Role.values()).filter(r -> r.name().equals(value)).findFirst()
                .orElse(null);
    }
}