package com.github.arlekinside.sqlver.app.service.impl;

import com.github.arlekinside.sqlver.app.config.security.SecurityRoles;
import com.github.arlekinside.sqlver.app.dto.UserDTO;
import com.github.arlekinside.sqlver.app.dto.UserStatsDTO;
import com.github.arlekinside.sqlver.app.entity.User;
import com.github.arlekinside.sqlver.app.exception.UserExistsException;
import com.github.arlekinside.sqlver.app.repo.UserRepository;
import com.github.arlekinside.sqlver.app.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserDTO userDTO) throws UserExistsException {
        return register(userDTO, SecurityRoles.USER);

    }

    @Override
    public User registerAdmin(UserDTO userDTO) throws UserExistsException {
        return register(userDTO, SecurityRoles.ADMIN);
    }

    public User register(UserDTO userDTO, SecurityRoles securityRole) throws UserExistsException {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new UserExistsException();
        }

        var user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(securityRole);
        user.setDateCreated(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        return userRepository.save(user);
    }

    @Override
    public UserStatsDTO getStats() {
        var res = new UserStatsDTO();
        var count = userRepository.count();

        res.setUsersCount(count);

        return res;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
