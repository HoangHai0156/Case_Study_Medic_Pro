package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    Customer create(LocationRegion locationRegion, Customer customer);

    Customer findCustomerByUserId(Long userId);

    List<Customer> findAllByUser_Id(Long userId);

    Boolean existsByEmail(String email);
}
