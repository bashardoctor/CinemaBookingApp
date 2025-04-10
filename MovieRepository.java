package com.hct.projects;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByGenre(String genre);
    List<Movie> findByReleaseDateAfter(LocalDate date);
    List<Movie> findByTitleContaining(String keyword);
}
