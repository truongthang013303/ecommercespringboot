package com.tbt.ecommerce.controller;

import com.tbt.ecommerce.dto.UserDTO;
import com.tbt.ecommerce.exception.UserException;
import com.tbt.ecommerce.model.User;
import com.tbt.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody UserDTO userDTO) throws UserException {
        UserDTO updated = userService.updateUser(jwt, userDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
} 
