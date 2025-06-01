package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.PasswordResetToken;
import com.mskn.vaadinspringproject.backend.services.base.IBaseService;

import java.util.Optional;

public interface IPasswordResetService extends IBaseService<PasswordResetToken> {
    void createResetToken(AppUser user);

    Optional<AppUser> validateToken(String token);

    void invalidateToken(String token);
}
