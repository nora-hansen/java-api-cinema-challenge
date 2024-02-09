package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public List<Movie> getAllMovies()   {
        return this.movieRepository.findAll();
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
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie)  {
        if(!movie.verifyMovie())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more required fields are null");

        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
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
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie)    {
        if(!movie.verifyMovie())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more required fields null");

        Movie movieToUpdate = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No movie with that id found")
                );

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<>(
                this.movieRepository.save(movieToUpdate),
                HttpStatus.CREATED
        );
    }

    /**
     * Delete a specified movie from the database
     *
     * @param id - ID of movie to delete
     * @return Response code signifying success/failure, and movie which was deleted
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id)  {
        Movie movieToDelete = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No movie with that id found"
                                )
                );
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
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
    public ResponseEntity<List<Screening>> getOneMovie(@PathVariable int id)  {
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Movie with id not found")
                );

        return ResponseEntity.ok(movie.getScreenings());
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
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening)    {
        if(!screening.verifyScreening())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more required fields are null"
            );

        Movie movieToScreen = this.movieRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Movie with that field found")
                );
        screening.setMovie(movieToScreen);
        movieToScreen.addScreening(screening);
        this.movieRepository.save(movieToScreen);
        this.screeningRepository.save(screening);

        return new ResponseEntity<>(screening, HttpStatus.CREATED);
    }

    /**
     * ! This is not in the spec, oops. Oh well, might include later
     * Updates a screening for the specified movie
     * Request Body:
     *  screenNumber: int REQUIRED
     *  capacity: int REQUIRED
     *  startsAt: Date REQUIRED
     *
     * @param id - ID of movie to add screening to
     * @param screening - Screening to add
     * @return Response code signifying success/failure, and screening which was added to the database
     */
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
