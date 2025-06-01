package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.repositories.IAppUserRepository;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;
import com.mskn.vaadinspringproject.backend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUser> implements IAppUserService {

    private final IAppUserRepository userRepository;

    @Autowired
    public AppUserServiceImpl(IAppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected IBaseRepository<AppUser> getRepository() {
        return userRepository;
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .disabled(!user.getActive())
                .build();
    }
}
