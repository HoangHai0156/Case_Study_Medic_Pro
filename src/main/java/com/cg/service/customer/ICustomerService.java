package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.service.IGeneralService;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    Customer create(LocationRegion locationRegion, Customer customer);
}
