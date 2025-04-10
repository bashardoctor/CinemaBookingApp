package com.hct.projects;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeRepository showtimeRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TheaterRepository theaterRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    // Get all showtimes
    @GetMapping
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        List<Showtime> showtimes = showtimeRepository.findAll();
        return new ResponseEntity<>(showtimes, HttpStatus.OK);
    }
    
    // Get showtime by ID
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable("id") int id) {
        Optional<Showtime> showtimeData = showtimeRepository.findById(id);
        if (showtimeData.isPresent()) {
            return new ResponseEntity<>(showtimeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new showtime (operation involving two tables)
    @PostMapping
    public ResponseEntity<Showtime> createShowtime(@RequestBody Showtime showtime, 
                                                  @RequestParam int movieId, 
                                                  @RequestParam int theaterId) {
        try {
            Optional<Movie> movieData = movieRepository.findById(movieId);
            Optional<Theater> theaterData = theaterRepository.findById(theaterId);
            
            if (!movieData.isPresent() || !theaterData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            showtime.setMovie(movieData.get());
            showtime.setTheater(theaterData.get());
            
            Showtime _showtime = showtimeRepository.save(showtime);
            return new ResponseEntity<>(_showtime, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a showtime
    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable("id") int id, @RequestBody Showtime showtime) {
        Optional<Showtime> showtimeData = showtimeRepository.findById(id);
        
        if (showtimeData.isPresent()) {
            Showtime _showtime = showtimeData.get();
            _showtime.setDate(showtime.getDate());
            _showtime.setTime(showtime.getTime());
            // Note: We don't update movie and theater relationships here to avoid accidental changes
            return new ResponseEntity<>(showtimeRepository.save(_showtime), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a showtime
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteShowtime(@PathVariable("id") int id) {
        try {
            showtimeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find showtimes by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Showtime>> findShowtimesByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Showtime> showtimes = showtimeRepository.findByDate(localDate);
            
            if (showtimes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(showtimes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all bookings for a showtime (operation involving two tables)
    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<Booking>> getShowtimeBookings(@PathVariable("id") int id) {
        try {
            Optional<Showtime> showtimeData = showtimeRepository.findById(id);
            if (!showtimeData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            List<Booking> bookings = bookingRepository.findByShowtime(showtimeData.get());
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
