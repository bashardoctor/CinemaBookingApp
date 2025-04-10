package com.hct.projects;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ShowtimeRepository showtimeRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    
    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") int id) {
        Optional<Booking> bookingData = bookingRepository.findById(id);
        if (bookingData.isPresent()) {
            return new ResponseEntity<>(bookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new booking (operation involving two tables)
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking, 
                                               @RequestParam int userId, 
                                               @RequestParam int showtimeId) {
        try {
            Optional<User> userData = userRepository.findById(userId);
            Optional<Showtime> showtimeData = showtimeRepository.findById(showtimeId);
            
            if (!userData.isPresent() || !showtimeData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            booking.setUser(userData.get());
            booking.setShowtime(showtimeData.get());
            booking.setBookingDate(LocalDate.now());
            booking.setStatus("PENDING");
            
            Booking _booking = bookingRepository.save(booking);
            return new ResponseEntity<>(_booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") int id, @RequestBody Booking booking) {
        Optional<Booking> bookingData = bookingRepository.findById(id);
        
        if (bookingData.isPresent()) {
            Booking _booking = bookingData.get();
            _booking.setSeatNumber(booking.getSeatNumber());
            _booking.setStatus(booking.getStatus());
            return new ResponseEntity<>(bookingRepository.save(_booking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable("id") int id) {
        try {
            bookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find bookings by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> findBookingsByStatus(@PathVariable String status) {
        try {
            List<Booking> bookings = bookingRepository.findByStatus(status);
            
            if (bookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all bookings for a user (operation involving two tables)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable("userId") int userId) {
        try {
            Optional<User> userData = userRepository.findById(userId);
            if (!userData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            List<Booking> bookings = bookingRepository.findByUser(userData.get());
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Apply promo code to booking (operation involving two tables)
    @PutMapping("/{id}/apply-promo")
    public ResponseEntity<Booking> applyPromoCode(@PathVariable("id") int id, @RequestParam String promoCode) {
        try {
            Optional<Booking> bookingData = bookingRepository.findById(id);
            PromoCode promo = promoCodeRepository.findByCode(promoCode);
            
            if (!bookingData.isPresent() || promo == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Check if promo code is valid (not expired)
            if (promo.getExpiryDate().isBefore(LocalDate.now())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            Booking _booking = bookingData.get();
            _booking.setPromoCode(promo);
            
            return new ResponseEntity<>(bookingRepository.save(_booking), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
