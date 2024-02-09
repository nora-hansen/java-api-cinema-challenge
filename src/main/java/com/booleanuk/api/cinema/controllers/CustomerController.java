package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Gets a list of all customers in the database
     * @return List of Customer objects
     */
    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    /**
     * Add a new customer to the database
     * Request Body:
     *  name: String REQUIRED
     *  email: String REQUIRED
     *  phone: String REQUIRED
     *
     * @param customer - The customer to be added to the database
     * @return Response signifying success/failure, and the customer which was added to the database
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer)  {
        if(!customer.verifyCustomer())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields are null"
            );

        return new ResponseEntity<>(
                this.customerRepository.save(customer),
                HttpStatus.CREATED
        );
    }


}
