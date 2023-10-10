package com.tbt.ecommerce.service;

import com.tbt.ecommerce.config.JwtProvider;
import com.tbt.ecommerce.converter.UserConverter;
import com.tbt.ecommerce.dto.UserDTO;
import com.tbt.ecommerce.exception.CartException;
import com.tbt.ecommerce.exception.OrderException;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.Order;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.CartRepository;
import com.tbt.ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private CartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.cartRepository=cartRepository;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return user.get();
        throw new UserException("user not found with id:" + userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserException("user not found with email:" + email);
        return user;
    }

    @Override
    public Page<User> getAllUserPagi(String sort, Integer pageNumber, Integer pageSize) {
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize < 1) pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);
        return page;
    }

    @Override
    public UserDTO updateUser(String jwt, UserDTO userDTO) {
        Optional<User> userOtp = null;
        if (userDTO != null) {
            userOtp = userRepository.findById(userDTO.getId());
        }
        User saved = null;
        if (userOtp.isPresent() && userOtp != null) {
            User user = userOtp.get();
            user.setFirstName(userDTO.getFirstName());
//            user.setFirstName(userDTO.getFirstName()!=""?userDTO.getFirstName():user.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setMobile(userDTO.getMobile());
            if (userDTO.getPassword() != "" && userDTO.getPassword().length() > 0 && !userDTO.getPassword().startsWith("$2a$10$")) {
                user.setPassword(userDTO.getPassword());
            }
            saved = userRepository.save(user);
        }
        if (saved != null) {
            return UserConverter.userEntityToUserDto(saved);
        }
        return null;
    }

    @Override
    public void deleteUser(Long userid) throws UserException, CartException {
        User user = findUserById(userid);
        if (user == null) {
            throw new UserException("userId:"+userid+" not found");
        }
        Cart cart = cartRepository.findByUserId(userid);
        cartRepository.delete(cart);
        userRepository.deleteById(userid);
    }
}
