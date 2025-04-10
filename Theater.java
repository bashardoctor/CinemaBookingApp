package com.hct.projects;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int theaterId;

    private String name;
    private int capacity;
    private String location;
    
    

    public int getTheaterId() {
		return theaterId;
	}



	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getCapacity() {
		return capacity;
	}



	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public List<Showtime> getShowtimes() {
		return showtimes;
	}



	public void setShowtimes(List<Showtime> showtimes) {
		this.showtimes = showtimes;
	}



	@OneToMany(mappedBy = "theater")  // One theater -> many showtimes
    private List<Showtime> showtimes;
}
