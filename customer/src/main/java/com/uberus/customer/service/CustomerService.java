package com.uberus.customer.service;

import com.uberus.customer.dto.FraudCheckResponse;
import com.uberus.customer.entity.Customer;
import com.uberus.customer.dto.CustomerRegistrationRequest;
import com.uberus.customer.repository.CustomerRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
        CustomerRepository db,
        RestTemplate restTemplate
) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        var customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        db.saveAndFlush(customer);
        restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}", FraudCheckResponse.class,  customer.getId());


    }
}


