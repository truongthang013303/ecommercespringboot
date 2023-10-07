package com.tbt.ecommerce.repository;

import com.tbt.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
