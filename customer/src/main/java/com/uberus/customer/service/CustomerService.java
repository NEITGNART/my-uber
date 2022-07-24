package com.uberus.customer.service;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import com.uberus.customer.dto.CustomerRegistrationRequest;
import com.uberus.customer.entity.Customer;
import com.uberus.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository db, RestTemplate restTemplate, FraudClient fraudClient,
                              NotificationClient notificationClient) {


    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        // todo: check if email valid
        // todo: check if email not taken
        db.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: make it async. i.e add to queue
        notificationClient.sendNotification(new NotificationRequest(customer.getId(), customer.getEmail(), String.format("Hi %s, welcome to Amigoscode...", customer.getFirstName())));

    }
}


