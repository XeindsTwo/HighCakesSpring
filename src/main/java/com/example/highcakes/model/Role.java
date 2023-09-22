package com.example.highcakes.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("Пользователь"),
    ADMIN("Администратор"),
    REVIEW_MODERATOR("Модератор отзывов"),
    CATALOG_MODERATOR("Модератор каталога");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}