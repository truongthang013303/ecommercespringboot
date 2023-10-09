package com.tbt.ecommerce.converter;

import com.tbt.ecommerce.dto.UserDTO;
import com.tbt.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static Page<UserDTO> toPageUserDto(Page<User> users) {
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
        return usersDTO;
    }

    public static User userDtoToUserEntity(UserDTO user) {
        User mapping = User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole())
                .build();

        return mapping;
    }

    public static UserDTO userEntityToUserDto(User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .role(user.getRole())
                .build();
        return userDTO;
    }
}
