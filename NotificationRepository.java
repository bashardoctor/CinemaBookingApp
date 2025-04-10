package com.hct.projects;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
    List<Notification> findByType(String type);
    List<Notification> findBySentDate(LocalDate sentDate);
    List<Notification> findByUserAndType(User user, String type);
    List<Notification> findBySentDateAfter(LocalDate date);
}
