package com.hct.projects;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUser(User user);
    List<Review> findByMovie(Movie movie);
    List<Review> findByRatingGreaterThanEqual(double rating);
    List<Review> findByReviewDateAfter(LocalDate date);
    List<Review> findByMovieAndRatingGreaterThanEqual(Movie movie, double rating);
}
