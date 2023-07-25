package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.repository.CustomerRepository;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LocationRegionRepository locationRegionRepository;
    @Override
    public Customer create(LocationRegion locationRegion, Customer customer) {
        LocationRegion locationRegion1 = locationRegionRepository.save(locationRegion);
        customer.setLocationRegion(locationRegion1);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public void delete(Customer customer) {

    }



    @Override
    public void deleteById(Long id) {

    }
}
