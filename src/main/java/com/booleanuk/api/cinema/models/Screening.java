package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

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

    @ManyToOne
    @JoinColumn(name = "screen_id")
    @JsonIgnoreProperties(value = {"id", "premium"})
    private Screen screen;

    @Column
    private ZonedDateTime startsAt;

    @Column
    @CreationTimestamp
    private ZonedDateTime createdAt;
    @PrePersist
    protected void onCreate()   {
        updatedAt = createdAt = ZonedDateTime.now();
    }

    @Column
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
    @PreUpdate
    protected void onUpdate()   {
        updatedAt = ZonedDateTime.now();
    }

    public Screening(
            Movie movie,
            Screen screen,
            String startsAt,
            int capacity
    )
    {
        this.movie          = movie;
        this.screen         = screen;
        TemporalAccessor ta = DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(startsAt);
        Instant i           = Instant.from(ta);
        this.startsAt       = ZonedDateTime.from(i);
    }

    public boolean verifyScreening()    {
        return this.getStartsAt() != null;
    }
}
