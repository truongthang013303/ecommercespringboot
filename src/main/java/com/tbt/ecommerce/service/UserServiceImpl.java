package com.tbt.ecommerce.service;

import com.tbt.ecommerce.config.JwtProvider;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
            return user.get();
        throw new UserException("user not found with id:"+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if(user==null)
            throw new UserException("user not found with email:"+email);
        return user;
    }

    @Override
    public Page<User> getAllUserPagi(String sort, Integer pageNumber, Integer pageSize) {
        if(pageNumber<0) pageNumber=0;
        if(pageSize<1) pageSize=1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);
        return page;
    }
}
