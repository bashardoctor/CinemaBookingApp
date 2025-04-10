package com.hct.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    // Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    
    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
        Optional<Payment> paymentData = paymentRepository.findById(id);
        if (paymentData.isPresent()) {
            return new ResponseEntity<>(paymentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new payment for a booking (operation involving two tables)
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment, @RequestParam int bookingId) {
        try {
            Optional<Booking> bookingData = bookingRepository.findById(bookingId);
            
            if (!bookingData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            payment.setBooking(bookingData.get());
            payment.setStatus("COMPLETED");
            
            Payment _payment = paymentRepository.save(payment);
            
            // Update booking status
            Booking booking = bookingData.get();
            booking.setStatus("CONFIRMED");
            bookingRepository.save(booking);
            
            return new ResponseEntity<>(_payment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") int id, @RequestBody Payment payment) {
        Optional<Payment> paymentData = paymentRepository.findById(id);
        
        if (paymentData.isPresent()) {
            Payment _payment = paymentData.get();
            _payment.setAmount(payment.getAmount());
            _payment.setStatus(payment.getStatus());
            _payment.setPaymentDate(payment.getPaymentDate());
            return new ResponseEntity<>(paymentRepository.save(_payment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable("id") int id) {
        try {
            paymentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find payments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> findPaymentsByStatus(@PathVariable String status) {
        try {
            List<Payment> payments = paymentRepository.findByStatus(status);
            
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get payment for a specific booking (operation involving two tables)
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Payment> getPaymentByBooking(@PathVariable("bookingId") int bookingId) {
        try {
            Optional<Booking> bookingData = bookingRepository.findById(bookingId);
            if (!bookingData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            List<Payment> payments = paymentRepository.findByBooking(bookingData.get());
            if (payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(payments.get(0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
