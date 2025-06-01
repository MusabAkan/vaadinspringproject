package com.mskn.vaadinspringproject.backend.security;

import com.mskn.vaadinspringproject.backend.entities.base.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

/**
 * IP ve Kullanıcı Takip
 */
public class AuditingListener {

    @PrePersist
    public void setCreatedInfo(BaseEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedByIp(RequestContextHolderUtil.getClientIp());
        entity.setUpdatedByIp(RequestContextHolderUtil.getClientIp());
        entity.setCreatedByUser(RequestContextHolderUtil.getCurrentUsername());
        entity.setUpdatedByUser(RequestContextHolderUtil.getCurrentUsername());
    }

    @PreUpdate
    public void setUpdatedInfo(BaseEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedByIp(RequestContextHolderUtil.getClientIp());
        entity.setUpdatedByUser(RequestContextHolderUtil.getCurrentUsername());
    }
}