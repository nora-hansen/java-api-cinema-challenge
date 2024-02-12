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

import java.util.List;

/*
    TODO

 */


@RestController
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ScreeningRepository screeningRepository;

    /*
        TODO
            Verify this. Does it actually work?
            Change so it returns a ResponseEntity
     */
    @GetMapping("customers/{customerId}/screenings/{screeningId}")
    public List<Ticket> getAllTickets(@PathVariable int customerId,
                                      @PathVariable int screeningId)  {
        return this.ticketRepository.findAllByCustomerIdAndScreeningId(customerId, screeningId);
    }


    @PostMapping("customers/{customerId}/screenings/{screeningId}")
    public ResponseEntity<Object> createTicket(
            @PathVariable int customerId,
            @PathVariable int screeningId,
            @RequestBody Ticket ticketBody)  {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No customer with that id were found"
                        )
                );
        Screening screening = this.screeningRepository.findById(screeningId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No screening with that id were found"
                        )
                );

        Ticket ticket = new Ticket(customer, screening, ticketBody.getNumSeats());
        this.ticketRepository.save(ticket);
        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                ticket
        );
    }
}
