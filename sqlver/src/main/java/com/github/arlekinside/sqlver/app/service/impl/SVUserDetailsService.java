package com.github.arlekinside.sqlver.app.service.impl;

import com.github.arlekinside.sqlver.logic.Utils;
import com.github.arlekinside.sqlver.app.config.security.SecurityRoles;
import com.github.arlekinside.sqlver.app.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SVUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SVUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found for: [%s]".formatted(username));
        }
        return user;
    }
}
