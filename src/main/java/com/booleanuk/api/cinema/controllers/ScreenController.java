package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screen;
import com.booleanuk.api.cinema.repositories.ScreenRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.ScreenListResponse;
import com.booleanuk.api.cinema.responses.ScreenResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("screens")
public class ScreenController {
    @Autowired
    private ScreenRepository screenRepository;

    /**
     * Get a list of all available screens
     * @return Response entity indicating success/failure
     */
    @GetMapping
    public ResponseEntity<Response<?>> getAllScreens()  {
        ScreenListResponse screenListResponse = new ScreenListResponse();
        screenListResponse.set(
                "Successfully returned a list of all screens",
                this.screenRepository.findAll()
        );
        return ResponseEntity.ok(screenListResponse);
    }

    /**
     * Create a new screen
     * @param screen - The screen to be created
     * @return Response entity indicating success/failure
     */
    @PostMapping
    public ResponseEntity<Response<?>> createScreen(@RequestBody Screen screen) {
        // Check if screen is valid
        if(screen.getCapacity() == 0
        || screen.getNumber() == 0) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("One or more required fields are null");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        this.screenRepository.save(screen);

        ScreenResponse screenResponse = new ScreenResponse();
        screenResponse.set("Successfully created screen", screen);
        return new ResponseEntity<>(
                screenResponse, HttpStatus.CREATED
        );
    }

    /**
     * Update an existing screen
     * @param id - The id of the screen to update
     * @param screen - The new screen values
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateScreen(@PathVariable int id, @RequestBody Screen screen)   {
        // Check if screen values are valid
        if(screen.getCapacity() == 0
                || screen.getNumber() == 0) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("One or more required fields are null");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Screen screenToUpdate = this.screenRepository.findById(id).orElse(null);
        if(screenToUpdate == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No screen with that id were found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        screenToUpdate.setCapacity(screen.getCapacity());
        screenToUpdate.setNumber(screen.getNumber());
        screenToUpdate.setPremium(screen.isPremium());

        ScreenResponse screenResponse = new ScreenResponse();
        screenResponse.set("Successfully updated screen", screenToUpdate);
        return new ResponseEntity<>(screenResponse, HttpStatus.CREATED);
    }

    /**
     * Delete a screen
     * @param id - Id of the screen to delete
     * @return Response entity indicating failure/success
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteScreen(@PathVariable int id)   {
        Screen screenToDelete = this.screenRepository.findById(id).orElse(null);
        if(screenToDelete == null)  {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No screen with that id were found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.screenRepository.delete(screenToDelete);

        ScreenResponse screenResponse = new ScreenResponse();
        screenResponse.set("Successfully deleted screen", screenToDelete);
        return ResponseEntity.ok(screenResponse);
    }
}
