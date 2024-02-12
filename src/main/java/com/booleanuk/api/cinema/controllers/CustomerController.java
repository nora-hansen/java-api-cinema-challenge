package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                "Successfully returned a list of all the customers",
                HttpStatus.OK,
                this.customerRepository.findAll()
        );
    }

    /**
     * Get one customer
     * @param id - ID of customer
     * @return Response Entity with the results of the request
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable int id)  {
        // Check customer id
        Customer customer = this.customerRepository.findById(id)
                .orElse(null);
        if(customer == null)    return ResponseHandler.generateException(
                "Error",
                "No customer with that id was found",
                HttpStatus.NOT_FOUND
        );

        return ResponseHandler.generateResponse(
                "Successfully found customer",
                HttpStatus.OK,
                customer
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
        // Check that all required fields are present
        if(!customer.verifyCustomer())
            return ResponseHandler.generateException(
                    "Error",
                    "Could not create a new customer, please check all fields are correct",
                    HttpStatus.BAD_REQUEST);
        this.customerRepository.save(customer);
        return ResponseHandler.generateResponse(
                "Successfully created a new customer",
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
        // Check the customer id
        Customer customerToUpdate = this.customerRepository.findById(id)
                .orElse(null);
        if (customerToUpdate == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No customer with that ID found",
                    HttpStatus.NOT_FOUND);
        // Update all the fields of the customer that are present in request body
        if(customer.getName()  != null) customerToUpdate.setName(customer.getName());
        if(customer.getEmail() != null) customerToUpdate.setEmail(customer.getEmail());
        if(customer.getPhone() != null) customerToUpdate.setPhone(customer.getPhone());

        return ResponseHandler.generateResponse(
                "Successfully updated the specified customer",
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
        // Check the customer id
        Customer customerToDelete = this.customerRepository.findById(id)
                .orElse(null);
        if (customerToDelete == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No customer with that ID found",
                    HttpStatus.NOT_FOUND);
        this.customerRepository.delete(customerToDelete);
        return ResponseHandler.generateResponse(
                "Successfully deleted the specified customer",
                HttpStatus.OK,
                customerToDelete
        );
    }
}
