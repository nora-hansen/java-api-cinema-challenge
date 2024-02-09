package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Screening functionality currently in movie controller!!!
*/

/*
    TODO
        Move screening stuff here
 */

/*
@RestController
@RequestMapping("{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Screening> getAllScreenings(@PathVariable int id)   {
        return this.screeningRepository.findAll();
    }
}
*/