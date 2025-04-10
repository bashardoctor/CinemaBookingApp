package com.hct.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/snackorders")
public class SnackOrderController {

    @Autowired
    private SnackOrderRepository snackOrderRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private SnackRepository snackRepository;
    
    // Get all snack orders
    @GetMapping
    public ResponseEntity<List<SnackOrder>> getAllSnackOrders() {
        List<SnackOrder> snackOrders = snackOrderRepository.findAll();
        return new ResponseEntity<>(snackOrders, HttpStatus.OK);
    }
    
    // Get snack order by ID
    @GetMapping("/{id}")
    public ResponseEntity<SnackOrder> getSnackOrderById(@PathVariable("id") int id) {
        Optional<SnackOrder> snackOrderData = snackOrderRepository.findById(id);
        if (snackOrderData.isPresent()) {
            return new ResponseEntity<>(snackOrderData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new snack order (operation involving two tables)
    @PostMapping
    public ResponseEntity<SnackOrder> createSnackOrder(@RequestBody SnackOrder snackOrder, 
                                                     @RequestParam int bookingId, 
                                                     @RequestParam int snackId) {
        try {
            Optional<Booking> bookingData = bookingRepository.findById(bookingId);
            Optional<Snack> snackData = snackRepository.findById(snackId);
            
            if (!bookingData.isPresent() || !snackData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            snackOrder.setBooking(bookingData.get());
            snackOrder.setSnack(snackData.get());
            
            SnackOrder _snackOrder = snackOrderRepository.save(snackOrder);
            return new ResponseEntity<>(_snackOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a snack order
    @PutMapping("/{id}")
    public ResponseEntity<SnackOrder> updateSnackOrder(@PathVariable("id") int id, @RequestBody SnackOrder snackOrder) {
        Optional<SnackOrder> snackOrderData = snackOrderRepository.findById(id);
        
        if (snackOrderData.isPresent()) {
            SnackOrder _snackOrder = snackOrderData.get();
            _snackOrder.setQuantity(snackOrder.getQuantity());
            return new ResponseEntity<>(snackOrderRepository.save(_snackOrder), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a snack order
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSnackOrder(@PathVariable("id") int id) {
        try {
            snackOrderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all snack orders for a booking (operation involving two tables)
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<SnackOrder>> getBookingSnackOrders(@PathVariable("bookingId") int bookingId) {
        try {
            Optional<Booking> bookingData = bookingRepository.findById(bookingId);
            if (!bookingData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            List<SnackOrder> snackOrders = snackOrderRepository.findByBooking(bookingData.get());
            return new ResponseEntity<>(snackOrders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
