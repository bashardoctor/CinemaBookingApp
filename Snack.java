package com.hct.projects;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;





@Entity
public class Snack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int snackId;

    private String name;
    private double price;
    private boolean availability;
    
    
    

    public int getSnackId() {
		return snackId;
	}




	public void setSnackId(int snackId) {
		this.snackId = snackId;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public boolean isAvailability() {
		return availability;
	}




	public void setAvailability(boolean availability) {
		this.availability = availability;
	}




	public List<SnackOrder> getSnackOrders() {
		return snackOrders;
	}




	public void setSnackOrders(List<SnackOrder> snackOrders) {
		this.snackOrders = snackOrders;
	}




	@OneToMany(mappedBy = "snack")
    private List<SnackOrder> snackOrders;  // One snack -> many snack orders
}
