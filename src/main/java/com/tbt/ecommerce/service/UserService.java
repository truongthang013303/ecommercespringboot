package com.tbt.ecommerce.service;

import com.tbt.ecommerce.dto.UserDTO;
import com.tbt.ecommerce.exception.CartException;
import com.tbt.ecommerce.exception.OrderException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.data.domain.Page;

public interface UserService {

    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
    Page<User> getAllUserPagi(String sort, Integer pageNumber, Integer pageSize);
    public UserDTO updateUser(String jwt, UserDTO userDTO);
    public void deleteUser(Long userid) throws UserException, CartException;
}
