package com.hct.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByStatus(String status);
    List<Payment> findByAmountGreaterThan(double amount);
    List<Payment> findByBooking(Booking booking);
}
