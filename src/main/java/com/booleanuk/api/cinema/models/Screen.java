package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screens")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int number;

    @Column
    private int capacity;

    @Column
    private boolean premium;

    @OneToMany(mappedBy = "screen")
    @JsonIgnore
    private List<Screening> screenings;

    public Screen(
            int number,
            int capacity,
            boolean premium
    ) {
        this.number   = number;
        this.capacity = capacity;
        this.premium  = premium;
    }

    public void addScreening(Screening screening)   {
        this.screenings.add(screening);
    }
}
