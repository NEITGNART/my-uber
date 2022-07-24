package com.uberus.customer.service;

import com.amigoscode.clients.fraud.FraudClient;
import com.uberus.customer.dto.CustomerRegistrationRequest;
import com.uberus.customer.entity.Customer;
import com.uberus.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository db, RestTemplate restTemplate,FraudClient fraudClient) {


    public void registerCustomer(CustomerRegistrationRequest request) {
        var customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        db.saveAndFlush(customer);
        fraudClient.isFraudster(customer.getId());

    }
}


