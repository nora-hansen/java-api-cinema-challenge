package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    //
    //  Movie methods
    //

    /**
     * Get all movies in the database
     *
     * @return List of Movie objects
     */
    @GetMapping
    public ResponseEntity<Object> getAllMovies()   {
        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.OK,
                this.movieRepository.findAll()
                );
    }

    /**
     * Add a new movie to the database
     * Request Body:
     *  title: String REQUIRED
     *  rating: String REQUIRED
     *  description: String REQUIRED
     *  runtimeMins: int REQUIRED
     *
     * @param movie - Movie to add to database
     * @return Response code signifying success/failure, and movie which was added to database
     * @see Movie::verifyMovie()
     */
    @PostMapping
    public ResponseEntity<Object> createMovie(@RequestBody Movie movie)  {
        if(!movie.verifyMovie())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more required fields are null");

        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                movie);
    }

    /**
     * Updates an existing movie in the database (if found)
     * Request Body:
     *  title: String REQUIRED
     *  rating: String REQUIRED
     *  description: String REQUIRED
     *  runtimeMins: int REQUIRED
     *
     * @param id - Unique identifier of movie to edit (int)
     * @param movie - Updated movie
     * @return Response code signifying success/failure, and movie which was updated
     * @see Movie::verifyMovie()
     */
    @PutMapping("{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable int id, @RequestBody Movie movie)    {
        if(!movie.verifyMovie())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields null");

        Movie movieToUpdate = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No movie with that id found")
                );

        // Updates the values of the movie
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                movieToUpdate
        );
    }

    /**
     * Delete a specified movie from the database
     *
     * @param id - ID of movie to delete
     * @return Response code signifying success/failure, and movie which was deleted
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable int id)  {
        Movie movieToDelete = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No movie with that id found"
                                )
                );
        this.movieRepository.delete(movieToDelete);

        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                movieToDelete
        );
    }

    //
    //  Screening methods
    //

    /**
     * Gets all screenings for the specified movie
     * @param id - ID of movie
     * @return List of Screening objects
     */
    @GetMapping("{id}/screenings")
    public ResponseEntity<Object> getOneMovie(@PathVariable int id)  {
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Movie with id not found")
                );

        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.OK,
                movie
        );
    }

    /**
     * Creates a screening for the specified movie
     * Request Body:
     *  screenNumber: int REQUIRED
     *  capacity: int REQUIRED
     *  startsAt: Date REQUIRED
     *
     * @param id - ID of movie to add screening to
     * @param screening - Screening to add
     * @return Response code signifying success/failure, and screening which was added to the database
     */
    @PostMapping("{id}/screenings")
    public ResponseEntity<Object> createScreening(@PathVariable int id, @RequestBody Screening screening)    {
        if(!screening.verifyScreening())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields are null"
            );

        Movie movieToScreen = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Movie with that field found")
                );
        screening.setStartsAt(screening.getStartsAt());
        screening.setMovie(movieToScreen);
        movieToScreen.addScreening(screening);
        this.movieRepository.save(movieToScreen);
        this.screeningRepository.save(screening);
        System.out.println(screening.getStartsAt());

        return ResponseHandler.generateResponse(
                "Success",
                HttpStatus.CREATED,
                screening
        );
    }

    /*
    @PutMapping("{id}/screenings")
    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening)    {
        if(!screening.verifyScreening())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields are null"
            );

        Screening screeningToUpdate = this.screeningRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No screening with that id found"
                        )
                );
        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        screeningToUpdate.setCapacity(screening.getCapacity());
        screeningToUpdate.setStartsAt(screening.getStartsAt());

        return new ResponseEntity<>(
                this.screeningRepository.save(screeningToUpdate),
                HttpStatus.CREATED
        );
    }
    */

}
