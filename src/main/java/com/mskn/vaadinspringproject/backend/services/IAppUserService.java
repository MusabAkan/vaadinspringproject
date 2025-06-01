package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.services.base.IBaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAppUserService extends IBaseService<AppUser>, UserDetailsService {
    AppUser findByUsername(String username);
}
