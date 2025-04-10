package com.hct.projects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataPopulationController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TheaterRepository theaterRepository;
    
    @Autowired
    private ShowtimeRepository showtimeRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    
    @Autowired
    private SnackRepository snackRepository;
    
    @Autowired
    private SnackOrderRepository snackOrderRepository;
    
    // Populate all tables with sample data
    @PostMapping("/populate")
    public ResponseEntity<String> populateData() {
        try {
            // Clear existing data
            snackOrderRepository.deleteAll();
            paymentRepository.deleteAll();
            reviewRepository.deleteAll();
            notificationRepository.deleteAll();
            bookingRepository.deleteAll();
            showtimeRepository.deleteAll();
            movieRepository.deleteAll();
            theaterRepository.deleteAll();
            promoCodeRepository.deleteAll();
            snackRepository.deleteAll();
            userRepository.deleteAll();
            
            // Populate Users
            List<User> users = populateUsers();
            
            // Populate Movies
            List<Movie> movies = populateMovies();
            
            // Populate Theaters
            List<Theater> theaters = populateTheaters();
            
            // Populate Showtimes
            List<Showtime> showtimes = populateShowtimes(movies, theaters);
            
            // Populate PromoCode
            List<PromoCode> promoCodes = populatePromoCodes();
            
            // Populate Snacks
            List<Snack> snacks = populateSnacks();
            
            // Populate Bookings
            List<Booking> bookings = populateBookings(users, showtimes, promoCodes);
            
            // Populate Payments
            populatePayments(bookings);
            
            // Populate Reviews
            populateReviews(users, movies);
            
            // Populate Notifications
            populateNotifications(users);
            
            // Populate SnackOrders
            populateSnackOrders(bookings, snacks);
            
            return new ResponseEntity<>("All tables populated successfully with sample data!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error populating data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private List<User> populateUsers() {
        List<User> users = new ArrayList<>();
        
        // Create 5 users
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setName("User " + i);
            user.setEmail("user" + i + "@example.com");
            user.setPassword("password" + i);
            user.setRole(i == 1 ? "admin" : "customer");
            users.add(userRepository.save(user));
        }
        
        return users;
    }
    
    private List<Movie> populateMovies() {
        List<Movie> movies = new ArrayList<>();
        
        // Create 5 movies
        String[] titles = {"Avengers: Endgame", "The Shawshank Redemption", "Inception", "Pulp Fiction", "The Dark Knight"};
        String[] genres = {"Action", "Drama", "Sci-Fi", "Crime", "Action"};
        int[] durations = {181, 142, 148, 154, 152};
        
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setTitle(titles[i]);
            movie.setGenre(genres[i]);
            movie.setDuration(durations[i]);
            movie.setReleaseDate(LocalDate.now().minusDays(i * 30));
            movies.add(movieRepository.save(movie));
        }
        
        return movies;
    }
    
    private List<Theater> populateTheaters() {
        List<Theater> theaters = new ArrayList<>();
        
        // Create 5 theaters
        String[] names = {"IMAX Theater", "Standard Theater 1", "Standard Theater 2", "VIP Theater", "3D Theater"};
        int[] capacities = {300, 200, 200, 100, 250};
        String[] locations = {"Floor 1", "Floor 2", "Floor 2", "Floor 3", "Floor 1"};
        
        for (int i = 0; i < 5; i++) {
            Theater theater = new Theater();
            theater.setName(names[i]);
            theater.setCapacity(capacities[i]);
            theater.setLocation(locations[i]);
            theaters.add(theaterRepository.save(theater));
        }
        
        return theaters;
    }
    
    private List<Showtime> populateShowtimes(List<Movie> movies, List<Theater> theaters) {
        List<Showtime> showtimes = new ArrayList<>();
        
        // Create 5 showtimes
        for (int i = 0; i < 5; i++) {
            Showtime showtime = new Showtime();
            showtime.setDate(LocalDate.now().plusDays(i));
            showtime.setTime(LocalTime.of(12 + i, 0)); // 12:00, 13:00, etc.
            showtime.setMovie(movies.get(i));
            showtime.setTheater(theaters.get(i));
            showtimes.add(showtimeRepository.save(showtime));
        }
        
        return showtimes;
    }
    
    private List<PromoCode> populatePromoCodes() {
        List<PromoCode> promoCodes = new ArrayList<>();
        
        // Create 5 promo codes
        String[] codes = {"SUMMER10", "WELCOME20", "MOVIE15", "WEEKEND5", "SPECIAL25"};
        double[] discounts = {10.0, 20.0, 15.0, 5.0, 25.0};
        
        for (int i = 0; i < 5; i++) {
            PromoCode promoCode = new PromoCode();
            promoCode.setCode(codes[i]);
            promoCode.setDiscount(discounts[i]);
            promoCode.setExpiryDate(LocalDate.now().plusMonths(i + 1));
            promoCodes.add(promoCodeRepository.save(promoCode));
        }
        
        return promoCodes;
    }
    
    private List<Snack> populateSnacks() {
        List<Snack> snacks = new ArrayList<>();
        
        // Create 5 snacks
        String[] names = {"Popcorn", "Soda", "Nachos", "Candy", "Hot Dog"};
        double[] prices = {5.99, 3.99, 6.99, 4.99, 7.99};
        boolean[] availabilities = {true, true, true, true, false};
        
        for (int i = 0; i < 5; i++) {
            Snack snack = new Snack();
            snack.setName(names[i]);
            snack.setPrice(prices[i]);
            snack.setAvailability(availabilities[i]);
            snacks.add(snackRepository.save(snack));
        }
        
        return snacks;
    }
    
    private List<Booking> populateBookings(List<User> users, List<Showtime> showtimes, List<PromoCode> promoCodes) {
        List<Booking> bookings = new ArrayList<>();
        
        // Create 5 bookings
        String[] seatNumbers = {"A1", "B3", "C5", "D7", "E9"};
        String[] statuses = {"CONFIRMED", "CONFIRMED", "PENDING", "CONFIRMED", "CANCELLED"};
        
        for (int i = 0; i < 5; i++) {
            Booking booking = new Booking();
            booking.setSeatNumber(seatNumbers[i]);
            booking.setStatus(statuses[i]);
            booking.setBookingDate(LocalDate.now().minusDays(i));
            booking.setUser(users.get(i));
            booking.setShowtime(showtimes.get(i));
            
            // Apply promo code to some bookings
            if (i % 2 == 0) {
                booking.setPromoCode(promoCodes.get(i));
            }
            
            bookings.add(bookingRepository.save(booking));
        }
        
        return bookings;
    }
    
    private void populatePayments(List<Booking> bookings) {
        // Create 5 payments
        double[] amounts = {15.99, 12.99, 18.99, 20.99, 14.99};
        String[] statuses = {"COMPLETED", "COMPLETED", "PENDING", "COMPLETED", "REFUNDED"};
        
        for (int i = 0; i < 5; i++) {
            Payment payment = new Payment();
            payment.setAmount(amounts[i]);
            payment.setStatus(statuses[i]);
            payment.setBooking(bookings.get(i));
            paymentRepository.save(payment);
        }
    }
    
    private void populateReviews(List<User> users, List<Movie> movies) {
        // Create 5 reviews
        double[] ratings = {4.5, 5.0, 3.5, 4.0, 4.8};
        String[] reviewTexts = {
            "Great movie, loved the special effects!",
            "One of the best movies I've ever seen.",
            "Good movie but a bit too long.",
            "Interesting plot and good acting.",
            "Amazing cinematography and storyline!"
        };
        
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setRating(ratings[i]);
            review.setReviewText(reviewTexts[i]);
            review.setReviewDate(LocalDate.now().minusDays(i));
            review.setUser(users.get(i));
            review.setMovie(movies.get(i));
            reviewRepository.save(review);
        }
    }
    
    private void populateNotifications(List<User> users) {
        // Create 5 notifications
        String[] types = {"BOOKING", "PAYMENT", "PROMO", "REMINDER", "CANCELLATION"};
        String[] messages = {
            "Your booking has been confirmed.",
            "Payment received successfully.",
            "New promo code available: SUMMER10",
            "Reminder: Your movie starts in 2 hours.",
            "Your booking has been cancelled."
        };
        
        for (int i = 0; i < 5; i++) {
            Notification notification = new Notification();
            notification.setType(types[i]);
            notification.setMessage(messages[i]);
            notification.setSentDate(LocalDate.now().minusDays(i));
            notification.setUser(users.get(i));
            notificationRepository.save(notification);
        }
    }
    
    private void populateSnackOrders(List<Booking> bookings, List<Snack> snacks) {
        // Create 5 snack orders
        int[] quantities = {2, 1, 3, 2, 1};
        
        for (int i = 0; i < 5; i++) {
            SnackOrder snackOrder = new SnackOrder();
            snackOrder.setQuantity(quantities[i]);
            snackOrder.setBooking(bookings.get(i));
            snackOrder.setSnack(snacks.get(i));
            snackOrderRepository.save(snackOrder);
        }
    }
}
