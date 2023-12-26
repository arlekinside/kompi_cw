package com.github.arlekinside.sqlver.app.service;

import com.github.arlekinside.sqlver.app.dto.UserDTO;
import com.github.arlekinside.sqlver.app.dto.UserStatsDTO;
import com.github.arlekinside.sqlver.app.entity.User;
import com.github.arlekinside.sqlver.app.exception.UserExistsException;

public interface UserService {

    User registerUser(UserDTO userDTO) throws UserExistsException;

    User registerAdmin(UserDTO userDTO) throws UserExistsException;

    UserStatsDTO getStats();

    void save(User user);
}
