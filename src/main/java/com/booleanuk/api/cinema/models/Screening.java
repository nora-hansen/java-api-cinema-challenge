package com.booleanuk.api.cinema.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

/**
 * Class holding information about a screening
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Screening(
            Movie movie,
            int screenNumber,
            LocalDateTime startsAt,
            int capacity
    )
    {
        this.movie        = movie;
        this.screenNumber = screenNumber;
        this.startsAt     = startsAt;
        this.capacity     = capacity;
    }
}
