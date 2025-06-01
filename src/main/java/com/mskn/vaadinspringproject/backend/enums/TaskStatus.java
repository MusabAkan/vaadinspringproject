package com.mskn.vaadinspringproject.backend.enums;

public enum TaskStatus {
    OPEN("Açık"),
    IN_PROGRESS("Devam Ediyor"),
    DONE("Tamamlandı"),
    CANCELLED("İptal Edildi"),
    ON_HOLD("Beklemede"),
    REVIEW("İnceleniyor"),
    REOPENED("Yeniden Açıldı"),
    BLOCKED("Engellendi");

    private final String status;

    TaskStatus(String label) {
        this.status = label;
    }

    @Override
    public String toString() {
        return status != null ? status : "";
    }
    }