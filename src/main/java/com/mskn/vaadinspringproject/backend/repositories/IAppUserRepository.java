package com.mskn.vaadinspringproject.backend.repositories;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;

import java.util.Optional;

public interface IAppUserRepository extends IBaseRepository<AppUser> {
    Optional<AppUser> findByUsername(String username);
}
