package com.hct.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/snacks")
public class SnackController {

    @Autowired
    private SnackRepository snackRepository;
    
    // Get all snacks
    @GetMapping
    public ResponseEntity<List<Snack>> getAllSnacks() {
        List<Snack> snacks = snackRepository.findAll();
        return new ResponseEntity<>(snacks, HttpStatus.OK);
    }
    
    // Get snack by ID
    @GetMapping("/{id}")
    public ResponseEntity<Snack> getSnackById(@PathVariable("id") int id) {
        Optional<Snack> snackData = snackRepository.findById(id);
        if (snackData.isPresent()) {
            return new ResponseEntity<>(snackData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new snack
    @PostMapping
    public ResponseEntity<Snack> createSnack(@RequestBody Snack snack) {
        try {
            Snack _snack = snackRepository.save(snack);
            return new ResponseEntity<>(_snack, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a snack
    @PutMapping("/{id}")
    public ResponseEntity<Snack> updateSnack(@PathVariable("id") int id, @RequestBody Snack snack) {
        Optional<Snack> snackData = snackRepository.findById(id);
        
        if (snackData.isPresent()) {
            Snack _snack = snackData.get();
            _snack.setName(snack.getName());
            _snack.setPrice(snack.getPrice());
            _snack.setAvailability(snack.isAvailability());
            return new ResponseEntity<>(snackRepository.save(_snack), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a snack
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSnack(@PathVariable("id") int id) {
        try {
            snackRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find snacks by availability
    @GetMapping("/available/{availability}")
    public ResponseEntity<List<Snack>> findSnacksByAvailability(@PathVariable boolean availability) {
        try {
            List<Snack> snacks = snackRepository.findByAvailability(availability);
            
            if (snacks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(snacks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find snacks by price less than
    @GetMapping("/price/{price}")
    public ResponseEntity<List<Snack>> findSnacksByPriceLessThan(@PathVariable double price) {
        try {
            List<Snack> snacks = snackRepository.findByPriceLessThan(price);
            
            if (snacks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(snacks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
