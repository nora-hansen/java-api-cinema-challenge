package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonIgnore
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ssXXX")
    private Date startsAt;

    @Column
    private int capacity;

    @Column
    @CreationTimestamp
    private Date createdAt;

    @Column
    @UpdateTimestamp
    private Date updatedAt;

    public Screening(
            Movie movie,
            int screenNumber,
            Date startsAt,
            int capacity
    )
    {
        this.movie        = movie;
        this.screenNumber = screenNumber;
        this.startsAt     = startsAt;
        this.capacity     = capacity;
    }

    public boolean verifyScreening()    {
        return this.getScreenNumber() != 0
                && this.getCapacity() != 0
                && this.getStartsAt() != null;
    }
}
