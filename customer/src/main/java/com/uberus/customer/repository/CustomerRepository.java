package com.uberus.customer.repository;

import com.uberus.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
