package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByUserId(Long userId);
    List<Customer> findAllByUser_Id(Long userId);
    Boolean existsByEmail(String email);
}
