package com.hct.projects;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUser(User user);
    List<Booking> findByShowtime(Showtime showtime);
    List<Booking> findByStatus(String status);
    List<Booking> findByBookingDate(LocalDate bookingDate);
    List<Booking> findByUserAndStatus(User user, String status);
}
