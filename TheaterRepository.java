package com.hct.projects;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findByLocation(String location);
    List<Theater> findByCapacityGreaterThan(int capacity);
    List<Theater> findByNameContaining(String keyword);
}
