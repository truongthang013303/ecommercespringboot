package com.tbt.ecommerce.repository;

import com.tbt.ecommerce.model.Product;
import com.tbt.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE (u.firstName=:firstName OR :firstName='') AND (u.lastName=:lastName OR :lastName='') AND (u.email=:email OR :email='') AND (u.mobile=:mobile OR :mobile='')")
    public List<User> filterUsers(
            @Param("firstName")String firstName
            ,@Param("lastName")String lastName
            ,@Param("email")String email
            ,@Param("mobile")String mobile
    );
}
