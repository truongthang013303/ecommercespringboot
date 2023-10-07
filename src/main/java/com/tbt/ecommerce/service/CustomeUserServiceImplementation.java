package com.tbt.ecommerce.service;

import com.tbt.ecommerce.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeUserServiceImplementation implements UserDetailsService {
    private UserRepository userRepository;

    public CustomeUserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.tbt.ecommerce.model.User user = userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found with email -"+username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
