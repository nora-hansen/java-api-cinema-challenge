package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Class holding information about a movie
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @Column
    private int runtimeMins;

    @Column
    @CreationTimestamp
    private Date createdAt;

    @Column
    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Screening> screenings;

    public Movie(
            String title,
            String rating,
            String description,
            int runtimeMins
    )
    {
        this.title       = title;
        this.rating      = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
    }

    public Movie(int id)    {
        this.id = id;
    }

    public void addScreening(Screening screening)   {
        this.screenings.add(screening);
    }

    public boolean verifyMovie() {
        return this.getTitle() != null
                && this.getRating() != null
                && this.getDescription() != null
                && this.getRuntimeMins() != 0;
    }
}
