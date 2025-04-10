package com.hct.projects;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

//1. User Entity
@Entity
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int userId;
 
 @Column(nullable = false)
 private String name;

 @Column(nullable = false, unique = true)
 private String email;

 @Column(nullable = false)
 private String password;

 private String role;  // Role (e.g. customer, admin)
 
 
 

 public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public List<Booking> getBookings() {
	return bookings;
}

public void setBookings(List<Booking> bookings) {
	this.bookings = bookings;
}

public List<Notification> getNotifications() {
	return notifications;
}

public void setNotifications(List<Notification> notifications) {
	this.notifications = notifications;
}


public List<Review> getReviews() {
	return reviews;
}

public void setReviews(List<Review> reviews) {
	this.reviews = reviews;
}



@OneToMany(mappedBy = "user")   // One user -> many bookings
 private List<Booking> bookings;

 @OneToMany(mappedBy = "user")   // One user -> many notifications
 private List<Notification> notifications;

 @OneToMany(mappedBy = "user")    // One user -> many reviews
 private List<Review> reviews;
}
