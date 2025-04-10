package com.hct.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    @Autowired
    private TheaterRepository theaterRepository;
    
    @Autowired
    private ShowtimeRepository showtimeRepository;
    
    // Get all theaters
    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheaters() {
        List<Theater> theaters = theaterRepository.findAll();
        return new ResponseEntity<>(theaters, HttpStatus.OK);
    }
    
    // Get theater by ID
    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable("id") int id) {
        Optional<Theater> theaterData = theaterRepository.findById(id);
        if (theaterData.isPresent()) {
            return new ResponseEntity<>(theaterData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new theater
    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        try {
            Theater _theater = theaterRepository.save(theater);
            return new ResponseEntity<>(_theater, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a theater
    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheater(@PathVariable("id") int id, @RequestBody Theater theater) {
        Optional<Theater> theaterData = theaterRepository.findById(id);
        
        if (theaterData.isPresent()) {
            Theater _theater = theaterData.get();
            _theater.setName(theater.getName());
            _theater.setCapacity(theater.getCapacity());
            _theater.setLocation(theater.getLocation());
            return new ResponseEntity<>(theaterRepository.save(_theater), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a theater
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTheater(@PathVariable("id") int id) {
        try {
            theaterRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find theaters by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Theater>> findTheatersByLocation(@PathVariable String location) {
        try {
            List<Theater> theaters = theaterRepository.findByLocation(location);
            
            if (theaters.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(theaters, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all showtimes for a theater (operation involving two tables)
    @GetMapping("/{id}/showtimes")
    public ResponseEntity<List<Showtime>> getTheaterShowtimes(@PathVariable("id") int id) {
        try {
            Optional<Theater> theaterData = theaterRepository.findById(id);
            if (!theaterData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            List<Showtime> showtimes = showtimeRepository.findByTheater(theaterData.get());
            return new ResponseEntity<>(showtimes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
