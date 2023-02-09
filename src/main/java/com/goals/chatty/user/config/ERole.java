package com.goals.chatty.user.config;

import lombok.Getter;

public enum ERole {

    ROLE_MODERATOR("Moderator"),
    ROLE_USER("User");

    @Getter
    private final String name;

    ERole(final String name) {
        this.name = name;
    }
}
