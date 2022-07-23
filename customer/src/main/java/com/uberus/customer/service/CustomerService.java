package com.uberus.customer.service;

import com.uberus.customer.entity.Customer;
import com.uberus.customer.dto.CustomerRegistrationRequest;
import com.uberus.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository db) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        var customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        db.save(customer);
    }
}


