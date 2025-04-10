package com.hct.projects;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {
    List<Showtime> findByDate(LocalDate date);
    List<Showtime> findByMovie(Movie movie);
    List<Showtime> findByTheater(Theater theater);
    List<Showtime> findByDateAndMovie(LocalDate date, Movie movie);
    List<Showtime> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
