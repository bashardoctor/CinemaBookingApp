package com.hct.projects;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
    
    // Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") int id) {
        Optional<Review> reviewData = reviewRepository.findById(id);
        if (reviewData.isPresent()) {
            return new ResponseEntity<>(reviewData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new review (operation involving two tables)
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review, 
                                             @RequestParam int userId, 
                                             @RequestParam int movieId) {
        try {
            Optional<User> userData = userRepository.findById(userId);
            Optional<Movie> movieData = movieRepository.findById(movieId);
            
            if (!userData.isPresent() || !movieData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            review.setUser(userData.get());
            review.setMovie(movieData.get());
            review.setReviewDate(LocalDate.now());
            
            Review _review = reviewRepository.save(review);
            return new ResponseEntity<>(_review, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") int id, @RequestBody Review review) {
        Optional<Review> reviewData = reviewRepository.findById(id);
        
        if (reviewData.isPresent()) {
            Review _review = reviewData.get();
            _review.setRating(review.getRating());
            _review.setReviewText(review.getReviewText());
            return new ResponseEntity<>(reviewRepository.save(_review), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") int id) {
        try {
            reviewRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find reviews by rating
    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> findReviewsByRating(@PathVariable double rating) {
        try {
            List<Review> reviews = reviewRepository.findByRatingGreaterThanEqual(rating);
            
            if (reviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
