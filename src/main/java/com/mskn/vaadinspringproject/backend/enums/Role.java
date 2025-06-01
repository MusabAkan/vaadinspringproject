package com.mskn.vaadinspringproject.backend.enums;

public enum Role {
    ADMIN("Yönetici"),
    USER("Kullanıcı");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return  value != null ? value : "";
    }
}
