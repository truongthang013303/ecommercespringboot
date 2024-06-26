package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.config.JwtProvider;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.Cart;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.repository.UserRepository;
import com.tbt.ecommerce.request.LoginRequest;
import com.tbt.ecommerce.response.AuthResponse;
import com.tbt.ecommerce.service.CartService;
import com.tbt.ecommerce.service.CustomeUserDetailsServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;

    private JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder;
    private CustomeUserDetailsServiceImplementation customeUserDetailsServiceImplementation;

    private CartService cartService;

    public AuthController(UserRepository userRepository,
                          CustomeUserDetailsServiceImplementation customeUserDetailsServiceImplementation,
                          PasswordEncoder passwordEncoder,
                          JwtProvider jwtProvider,
                          CartService cartService
                          ) {
        this.userRepository = userRepository;
        this.customeUserDetailsServiceImplementation = customeUserDetailsServiceImplementation;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider=jwtProvider;
        this.cartService=cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser = userRepository.save(createdUser);

        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority("user")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //role(String) of user is null SO List<GrantedAuthority> of Authentication is null. When generate jwt token if List<GrantedAuthority> of Authentication is null scope="user"
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest)
    {
           String username = loginRequest.getEmail();
           String password = loginRequest.getPassword();
           Authentication authentication = authenticate(username, password);
           SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, "Signin Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = customeUserDetailsServiceImplementation.loadUserByUsername(username);
        if(userDetails==null){
            throw  new BadCredentialsException("Invalid Username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
