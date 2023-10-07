package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.dto.UserDTO;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<Page<UserDTO>> getAllUserPagi(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "1") Integer pageSize, @RequestParam(required = false) String sort) throws UserException {
        Page<User> users = userService.getAllUserPagi(sort, pageNumber, pageSize);
        List<UserDTO> mapping = users.getContent().stream().map(user -> UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole())
                .build()
        ).collect(Collectors.toList());

        Page<UserDTO> usersDTO = new PageImpl<>(mapping, users.getPageable(), users.getTotalElements());
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
}
