package com.tbt.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tbt.ecommerce.model.Address;
import com.tbt.ecommerce.model.PaymentInformation;
import com.tbt.ecommerce.model.Rating;
import com.tbt.ecommerce.model.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String mobile;
    private String password;
    private LocalDateTime createdAt;
}
