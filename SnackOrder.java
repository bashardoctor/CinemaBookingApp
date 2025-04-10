package com.hct.projects;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class SnackOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int snackOrderId;

    private int quantity;
    
    

    public int getSnackOrderId() {
		return snackOrderId;
	}

	public void setSnackOrderId(int snackOrderId) {
		this.snackOrderId = snackOrderId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Snack getSnack() {
		return snack;
	}

	public void setSnack(Snack snack) {
		this.snack = snack;
	}

	@ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;  // Each snack order belongs to a booking

    @ManyToOne
    @JoinColumn(name = "snack_id")
    private Snack snack; // Each snack order links to one snack
}