package com.tbt.ecommerce.service;

import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.data.domain.Page;

public interface UserService {

    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
    Page<User> getAllUserPagi(String sort, Integer pageNumber, Integer pageSize);
}
