# Cinema Booking Application - Deliverable 2: Back-End REST API & Database

## Overview

This document provides a comprehensive overview of the back-end REST API and database implementation for the Cinema Booking Application. The implementation includes:

1. Database design with Spring Data JPA entities
2. Repository interfaces for database access
3. RESTful endpoints for various operations
4. Data population functionality
5. Testing documentation

## Database Implementation

### Entity Classes

The database schema is implemented using the following JPA entities:

1. **User** - Represents system users (customers and admins)
2. **Movie** - Represents movies available for booking
3. **Theater** - Represents physical theaters where movies are shown
4. **Showtime** - Represents specific movie showings in theaters
5. **Booking** - Represents customer bookings for showtimes
6. **Payment** - Represents payments for bookings
7. **Review** - Represents user reviews for movies
8. **Notification** - Represents notifications sent to users
9. **PromoCode** - Represents promotional codes for discounts
10. **Snack** - Represents snacks available for purchase
11. **SnackOrder** - Represents snack orders associated with bookings

All entities include appropriate JPA annotations for:
- Primary keys with auto-generation
- Required fields with not-null constraints
- Unique constraints where needed
- Proper relationship mappings (OneToMany, ManyToOne, OneToOne)

### Entity Relationships

The following relationships are implemented:

- One User can have many Bookings, Reviews, and Notifications
- One Movie can have many Showtimes and Reviews
- One Theater can have many Showtimes
- One Showtime can have many Bookings
- One Booking can have one Payment, many SnackOrders, and one PromoCode
- One PromoCode can be used for many Bookings
- One Snack can be in many SnackOrders

## Repository Implementation

Repository interfaces are implemented for all entities, extending JpaRepository to provide standard CRUD operations and custom query methods:

1. **UserRepository** - Find users by email and role
2. **MovieRepository** - Find movies by genre, release date, and title
3. **TheaterRepository** - Find theaters by location and capacity
4. **ShowtimeRepository** - Find showtimes by date, movie, and theater
5. **BookingRepository** - Find bookings by user, showtime, and status
6. **PaymentRepository** - Find payments by status and booking
7. **ReviewRepository** - Find reviews by user, movie, and rating
8. **NotificationRepository** - Find notifications by user and type
9. **PromoCodeRepository** - Find promo codes by code and expiry date
10. **SnackRepository** - Find snacks by availability and price
11. **SnackOrderRepository** - Find snack orders by booking and snack

## REST API Implementation

### Controllers

REST controllers are implemented for all entities, providing endpoints for various operations:

1. **UserController** - Manage users
2. **MovieController** - Manage movies and their reviews
3. **TheaterController** - Manage theaters and their showtimes
4. **ShowtimeController** - Manage showtimes and their bookings
5. **BookingController** - Manage bookings, apply promo codes
6. **PaymentController** - Manage payments for bookings
7. **ReviewController** - Manage movie reviews
8. **NotificationController** - Manage user notifications
9. **PromoCodeController** - Manage promotional codes
10. **SnackController** - Manage available snacks
11. **SnackOrderController** - Manage snack orders for bookings
12. **DataPopulationController** - Populate the database with sample data

### Endpoints

The implementation includes more than the required 10 RESTful endpoints:

#### Single Table Operations:
1. GET /api/users - Get all users
2. GET /api/movies - Get all movies
3. GET /api/theaters - Get all theaters
4. GET /api/users/{id} - Get user by ID
5. POST /api/movies - Create a new movie
6. PUT /api/theaters/{id} - Update a theater
7. DELETE /api/users/{id} - Delete a user
8. GET /api/movies/genre/{genre} - Find movies by genre
9. GET /api/theaters/location/{location} - Find theaters by location
10. GET /api/promocodes/valid - Find valid promo codes

#### Two-Table Operations:
1. GET /api/movies/{id}/reviews - Get all reviews for a movie
2. GET /api/theaters/{id}/showtimes - Get all showtimes for a theater
3. GET /api/showtimes/{id}/bookings - Get all bookings for a showtime
4. GET /api/bookings/user/{userId} - Get all bookings for a user
5. PUT /api/bookings/{id}/apply-promo - Apply promo code to booking
6. POST /api/payments?bookingId={id} - Create payment for a booking
7. GET /api/payments/booking/{bookingId} - Get payment for a booking
8. POST /api/reviews?userId={id}&movieId={id} - Create review for a movie by a user
9. GET /api/notifications/user/{userId} - Get all notifications for a user
10. GET /api/snackorders/booking/{bookingId} - Get all snack orders for a booking

### Data Population

A dedicated controller (DataPopulationController) is implemented to populate all tables with sample data. The endpoint `/api/data/populate` adds 5 records to each table, respecting the relationships between entities.

## Testing Documentation

Comprehensive testing documentation is provided in the `testing_documentation.md` file, which includes:

1. Instructions for testing each endpoint
2. Example request payloads for POST/PUT operations
3. Expected responses for all operations
4. JSON format for requests and responses

## Conclusion

The implemented back-end REST API and database for the Cinema Booking Application meets all the requirements specified in the assignment:

1. Database tables are created using Spring Data JPA entities with appropriate annotations
2. More than 10 RESTful endpoints are implemented, including operations involving both single and multiple tables
3. Data population functionality is provided to add at least 5 records to each table
4. Comprehensive testing documentation is provided

The implementation follows best practices for Spring Boot applications, including proper separation of concerns between entities, repositories, and controllers.
