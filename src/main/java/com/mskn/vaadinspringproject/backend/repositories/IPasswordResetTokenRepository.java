package com.mskn.vaadinspringproject.backend.repositories;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.PasswordResetToken;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;

import java.util.Optional;

public interface IPasswordResetTokenRepository extends IBaseRepository<PasswordResetToken> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(AppUser user);
}
