package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Class holding information about a screening
 * TODO
 *  Fix returned date format
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
    private Date startsAt;

    @Column
    private int capacity;

    @Column
    @CreationTimestamp
    private Date createdAt;
    @PrePersist
    protected void onCreate()   {
        updatedAt = createdAt = new Date();
    }

    @Column
    @UpdateTimestamp
    private Date updatedAt;
    @PreUpdate
    protected void onUpdate()   {
        updatedAt = new Date();
    }

    public Screening(
            Movie movie,
            int screenNumber,
            String startsAt,
            int capacity
    )
    {

        this.movie        = movie;
        this.screenNumber = screenNumber;
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(startsAt);
        Instant i = Instant.from(ta);
        this.startsAt = Date.from(i);
        this.capacity     = capacity;
    }

    public boolean verifyScreening()    {
        return this.getScreenNumber() != 0
                && this.getCapacity() != 0
                && this.getStartsAt() != null;
    }
}
