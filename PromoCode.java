package com.hct.projects;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;




@Entity
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promoCodeId;

    private String code;
    private double discount;
    private LocalDate expiryDate;
    
    

    public int getPromoCodeId() {
		return promoCodeId;
	}



	public void setPromoCodeId(int promoCodeId) {
		this.promoCodeId = promoCodeId;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public double getDiscount() {
		return discount;
	}



	public void setDiscount(double discount) {
		this.discount = discount;
	}



	public LocalDate getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}



	public List<Booking> getBookings() {
		return bookings;
	}



	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}



	@OneToMany(mappedBy = "promoCode")
    private List<Booking> bookings;   // One promo -> many bookings
}

