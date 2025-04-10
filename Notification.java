package com.hct.projects;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    private String type;
    private String message;
    private LocalDate sentDate;
    

    public int getNotificationId() {
		return notificationId;
	}


	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public LocalDate getSentDate() {
		return sentDate;
	}


	public void setSentDate(LocalDate sentDate) {
		this.sentDate = sentDate;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;// Notifications are sent to users
}
