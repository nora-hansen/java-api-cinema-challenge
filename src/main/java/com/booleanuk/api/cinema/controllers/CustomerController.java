package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    /**
     * Gets a list of all customers in the database
     * @return List of Customer objects
     */
    @GetMapping
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.OK,
                this.customerRepository.findAll()
        );
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
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer)  {
        if(!customer.verifyCustomer())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields are null"
            );
        this.customerRepository.save(customer);
        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                customer
        );
    }

    /**
     * Update an existing customer to the database
     * Request Body:
     *  name: String REQUIRED
     *  email: String REQUIRED
     *  phone: String REQUIRED
     *
     * @param customer - The customer to be added to the database
     * @return Response signifying success/failure, and the customer which was added to the database
     */
    @PutMapping("{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int id, @RequestBody Customer customer)    {
        if(!customer.verifyCustomer())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields are null"
            );

        Customer customerToUpdate = this.customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No customer with that id was found")
                );

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());

        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                this.customerRepository.save(customerToUpdate)
        );
    }

    /**
     * Deletes a customer by the given id
     * @param id - ID of customer to delete
     * @return Response indicating success/failure, and the deleted customer
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable int id)    {
        Customer customerToDelete = this.customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No customer matching that id were found")
                );
        this.customerRepository.delete(customerToDelete);
        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.OK,
                customerToDelete
        );
    }
}
