package com.hct.projects;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Integer> {
    List<Snack> findByAvailability(boolean availability);
    List<Snack> findByPriceLessThan(double price);
    List<Snack> findByNameContaining(String keyword);
}
