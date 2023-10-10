package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.config.JwtProvider;
import com.tbt.ecommerce.converter.UserConverter;
import com.tbt.ecommerce.dto.UserDTO;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.UserRepository;
import com.tbt.ecommerce.response.AuthResponse;
import com.tbt.ecommerce.service.CartService;
import com.tbt.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartService cartService;

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody UserDTO userDTO) throws UserException {
        UserDTO updated = userService.updateUser(jwt, userDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String mobile = user.getMobile();
        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setMobile(mobile);
        User savedUser = userRepository.save(createdUser);

        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

/*        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");*/
        return new ResponseEntity<>(UserConverter.userEntityToUserDto(savedUser), HttpStatus.CREATED);
    }
}
