package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.responses.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    /**
     * Get all tickets for a given customer and screening
     * @param customerId - ID of the customer
     * @param screeningId - ID of the screening
     * @return ResponseEntity showing the results of the query
     */
    @GetMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Object> getAllTickets(@PathVariable int customerId,
                                      @PathVariable int screeningId)  {
        // Check the customer
        Customer customer = this.customerRepository.findById(customerId)
                .orElse(null);
        if (customer == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No ticket found for the customer and screening with those ids found",
                    HttpStatus.NOT_FOUND);
        // Check the screening
        Screening screening = this.screeningRepository.findById(screeningId)
                .orElse(null);
        if (screening == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No ticket found for the customer and screening with those ids found",
                    HttpStatus.NOT_FOUND);

        return ResponseHandler.generateResponse(
                "Successfully returned a list of all tickets for a given customer and screening",
                HttpStatus.OK,
                this.ticketRepository.findAllByCustomerIdAndScreeningId(
                        customerId,
                        screeningId));
    }

    /**
     * Create a ticket for the given customer and screening
     * Request Body:
     *  numSeats Int REQUIRED
     * @param customerId - ID for the scpecified customer
     * @param screeningId - ID for the specified screening
     * @param ticketBody - Request Body with the amount of nums
     * @return Response Entity with the results of the request
     */
    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Object> createTicket(
            @PathVariable int customerId,
            @PathVariable int screeningId,
            @RequestBody Ticket ticketBody)  {
        if(ticketBody.getNumSeats() == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Required field \"numSeats\" cannot be null");
        // Check the customer id
        Customer customer = this.customerRepository.findById(customerId)
                .orElse(null);
        if (customer == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No customer or screening with those ids found",
                    HttpStatus.NOT_FOUND);
        // Check the screening id
        Screening screening = this.screeningRepository.findById(screeningId)
                .orElse(null);
        if (screening == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No customer or screening with those ids found",
                    HttpStatus.NOT_FOUND);

        Ticket ticket = new Ticket(customer, screening, ticketBody.getNumSeats());
        this.ticketRepository.save(ticket);
        return ResponseHandler.generateResponse(
                "Successfully created a ticket for the movie and screening specified",
                HttpStatus.CREATED,
                ticket
        );
    }
}
