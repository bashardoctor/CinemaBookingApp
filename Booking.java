package com.hct.projects;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;




@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private String seatNumber;
    private String status;
    private LocalDate bookingDate;

    public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Showtime getShowtime() {
		return showtime;
	}

	public void setShowtime(Showtime showtime) {
		this.showtime = showtime;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PromoCode getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(PromoCode promoCode) {
		this.promoCode = promoCode;
	}

	public List<SnackOrder> getSnackOrders() {
		return snackOrders;
	}

	public void setSnackOrders(List<SnackOrder> snackOrders) {
		this.snackOrders = snackOrders;
	}

	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;   // Each booking made by one user

 
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;  // Each booking is for one showtime

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;   // One-to-one: booking ↔ payment

    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;   // One promo code per booking

    @OneToMany(mappedBy = "booking")
    private List<SnackOrder> snackOrders;  // One booking -> many snack orders
}