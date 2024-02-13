package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Integer> {
}
