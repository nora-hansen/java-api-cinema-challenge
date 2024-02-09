package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.repositories.CustomerRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("customer/{customerId}/screening/{screeningId}")
    public List<Ticket> getAllTickets(@PathVariable int customerId,
                                      @PathVariable int screeningId)  {
        return this.ticketRepository.findAllByCustomerIdAndScreeningId(customerId, screeningId);
    }

    /*
        TODO
            PostMapping
     */
}
