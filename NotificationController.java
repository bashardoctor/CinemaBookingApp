package com.hct.projects;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    
    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("id") int id) {
        Optional<Notification> notificationData = notificationRepository.findById(id);
        if (notificationData.isPresent()) {
            return new ResponseEntity<>(notificationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new notification for a user (operation involving two tables)
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification, 
                                                         @RequestParam int userId) {
        try {
            Optional<User> userData = userRepository.findById(userId);
            
            if (!userData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            notification.setUser(userData.get());
            notification.setSentDate(LocalDate.now());
            
            Notification _notification = notificationRepository.save(notification);
            return new ResponseEntity<>(_notification, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a notification
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("id") int id, @RequestBody Notification notification) {
        Optional<Notification> notificationData = notificationRepository.findById(id);
        
        if (notificationData.isPresent()) {
            Notification _notification = notificationData.get();
            _notification.setType(notification.getType());
            _notification.setMessage(notification.getMessage());
            return new ResponseEntity<>(notificationRepository.save(_notification), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a notification
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable("id") int id) {
        try {
            notificationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all notifications for a user (operation involving two tables)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("userId") int userId) {
        try {
            Optional<User> userData = userRepository.findById(userId);
            if (!userData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            List<Notification> notifications = notificationRepository.findByUser(userData.get());
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
