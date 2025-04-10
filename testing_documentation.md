# Cinema Booking Application - REST API Testing Documentation

This document provides comprehensive testing instructions for all REST endpoints implemented in the Cinema Booking Application. For each endpoint, we include:

1. The HTTP method and URL
2. Example request payload (for POST/PUT requests)
3. Expected response
4. Testing instructions

## Table of Contents

1. [Data Population](#data-population)
2. [User Endpoints](#user-endpoints)
3. [Movie Endpoints](#movie-endpoints)
4. [Theater Endpoints](#theater-endpoints)
5. [Showtime Endpoints](#showtime-endpoints)
6. [Booking Endpoints](#booking-endpoints)
7. [Payment Endpoints](#payment-endpoints)
8. [Review Endpoints](#review-endpoints)
9. [Notification Endpoints](#notification-endpoints)
10. [PromoCode Endpoints](#promocode-endpoints)
11. [Snack Endpoints](#snack-endpoints)
12. [SnackOrder Endpoints](#snackorder-endpoints)

## Data Population

Before testing individual endpoints, we should populate the database with sample data.

### Populate All Tables

**Request:**
```
POST http://localhost:8080/api/data/populate
```

**Expected Response:**
```json
"All tables populated successfully with sample data!"
```

**Testing Instructions:**
1. Send a POST request to the endpoint
2. Verify that the response indicates successful population
3. This endpoint will add 5 records to each table in the database

## User Endpoints

### Get All Users

**Request:**
```
GET http://localhost:8080/api/users
```

**Expected Response:**
```json
[
  {
    "userId": 1,
    "name": "User 1",
    "email": "user1@example.com",
    "password": "password1",
    "role": "admin",
    "bookings": [],
    "notifications": [],
    "reviews": []
  },
  {
    "userId": 2,
    "name": "User 2",
    "email": "user2@example.com",
    "password": "password2",
    "role": "customer",
    "bookings": [],
    "notifications": [],
    "reviews": []
  },
  ...
]
```

### Get User by ID

**Request:**
```
GET http://localhost:8080/api/users/1
```

**Expected Response:**
```json
{
  "userId": 1,
  "name": "User 1",
  "email": "user1@example.com",
  "password": "password1",
  "role": "admin",
  "bookings": [],
  "notifications": [],
  "reviews": []
}
```

### Create User

**Request:**
```
POST http://localhost:8080/api/users
```

**Request Body:**
```json
{
  "name": "New User",
  "email": "newuser@example.com",
  "password": "newpassword",
  "role": "customer"
}
```

**Expected Response:**
```json
{
  "userId": 6,
  "name": "New User",
  "email": "newuser@example.com",
  "password": "newpassword",
  "role": "customer",
  "bookings": null,
  "notifications": null,
  "reviews": null
}
```

### Update User

**Request:**
```
PUT http://localhost:8080/api/users/6
```

**Request Body:**
```json
{
  "name": "Updated User",
  "email": "updateduser@example.com",
  "password": "updatedpassword",
  "role": "customer"
}
```

**Expected Response:**
```json
{
  "userId": 6,
  "name": "Updated User",
  "email": "updateduser@example.com",
  "password": "updatedpassword",
  "role": "customer",
  "bookings": [],
  "notifications": [],
  "reviews": []
}
```

### Delete User

**Request:**
```
DELETE http://localhost:8080/api/users/6
```

**Expected Response:**
```
204 No Content
```

### Find Users by Role

**Request:**
```
GET http://localhost:8080/api/users/role/admin
```

**Expected Response:**
```json
[
  {
    "userId": 1,
    "name": "User 1",
    "email": "user1@example.com",
    "password": "password1",
    "role": "admin",
    "bookings": [],
    "notifications": [],
    "reviews": []
  }
]
```

## Movie Endpoints

### Get All Movies

**Request:**
```
GET http://localhost:8080/api/movies
```

**Expected Response:**
```json
[
  {
    "movieId": 1,
    "title": "Avengers: Endgame",
    "genre": "Action",
    "duration": 181,
    "releaseDate": "2025-04-10",
    "showtimes": [],
    "reviews": []
  },
  {
    "movieId": 2,
    "title": "The Shawshank Redemption",
    "genre": "Drama",
    "duration": 142,
    "releaseDate": "2025-03-11",
    "showtimes": [],
    "reviews": []
  },
  ...
]
```

### Get Movie by ID

**Request:**
```
GET http://localhost:8080/api/movies/1
```

**Expected Response:**
```json
{
  "movieId": 1,
  "title": "Avengers: Endgame",
  "genre": "Action",
  "duration": 181,
  "releaseDate": "2025-04-10",
  "showtimes": [],
  "reviews": []
}
```

### Create Movie

**Request:**
```
POST http://localhost:8080/api/movies
```

**Request Body:**
```json
{
  "title": "New Movie",
  "genre": "Comedy",
  "duration": 120,
  "releaseDate": "2025-05-15"
}
```

**Expected Response:**
```json
{
  "movieId": 6,
  "title": "New Movie",
  "genre": "Comedy",
  "duration": 120,
  "releaseDate": "2025-05-15",
  "showtimes": null,
  "reviews": null
}
```

### Update Movie

**Request:**
```
PUT http://localhost:8080/api/movies/6
```

**Request Body:**
```json
{
  "title": "Updated Movie",
  "genre": "Comedy",
  "duration": 125,
  "releaseDate": "2025-05-20"
}
```

**Expected Response:**
```json
{
  "movieId": 6,
  "title": "Updated Movie",
  "genre": "Comedy",
  "duration": 125,
  "releaseDate": "2025-05-20",
  "showtimes": [],
  "reviews": []
}
```

### Delete Movie

**Request:**
```
DELETE http://localhost:8080/api/movies/6
```

**Expected Response:**
```
204 No Content
```

### Find Movies by Genre

**Request:**
```
GET http://localhost:8080/api/movies/genre/Action
```

**Expected Response:**
```json
[
  {
    "movieId": 1,
    "title": "Avengers: Endgame",
    "genre": "Action",
    "duration": 181,
    "releaseDate": "2025-04-10",
    "showtimes": [],
    "reviews": []
  },
  {
    "movieId": 5,
    "title": "The Dark Knight",
    "genre": "Action",
    "duration": 152,
    "releaseDate": "2025-02-09",
    "showtimes": [],
    "reviews": []
  }
]
```

### Get Movie Reviews (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/movies/1/reviews
```

**Expected Response:**
```json
[
  {
    "reviewId": 1,
    "rating": 4.5,
    "reviewText": "Great movie, loved the special effects!",
    "reviewDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1",
      "email": "user1@example.com"
    },
    "movie": {
      "movieId": 1,
      "title": "Avengers: Endgame"
    }
  }
]
```

## Theater Endpoints

### Get All Theaters

**Request:**
```
GET http://localhost:8080/api/theaters
```

**Expected Response:**
```json
[
  {
    "theaterId": 1,
    "name": "IMAX Theater",
    "capacity": 300,
    "location": "Floor 1",
    "showtimes": []
  },
  {
    "theaterId": 2,
    "name": "Standard Theater 1",
    "capacity": 200,
    "location": "Floor 2",
    "showtimes": []
  },
  ...
]
```

### Get Theater by ID

**Request:**
```
GET http://localhost:8080/api/theaters/1
```

**Expected Response:**
```json
{
  "theaterId": 1,
  "name": "IMAX Theater",
  "capacity": 300,
  "location": "Floor 1",
  "showtimes": []
}
```

### Create Theater

**Request:**
```
POST http://localhost:8080/api/theaters
```

**Request Body:**
```json
{
  "name": "New Theater",
  "capacity": 150,
  "location": "Floor 4"
}
```

**Expected Response:**
```json
{
  "theaterId": 6,
  "name": "New Theater",
  "capacity": 150,
  "location": "Floor 4",
  "showtimes": null
}
```

### Update Theater

**Request:**
```
PUT http://localhost:8080/api/theaters/6
```

**Request Body:**
```json
{
  "name": "Updated Theater",
  "capacity": 175,
  "location": "Floor 4"
}
```

**Expected Response:**
```json
{
  "theaterId": 6,
  "name": "Updated Theater",
  "capacity": 175,
  "location": "Floor 4",
  "showtimes": []
}
```

### Delete Theater

**Request:**
```
DELETE http://localhost:8080/api/theaters/6
```

**Expected Response:**
```
204 No Content
```

### Find Theaters by Location

**Request:**
```
GET http://localhost:8080/api/theaters/location/Floor 1
```

**Expected Response:**
```json
[
  {
    "theaterId": 1,
    "name": "IMAX Theater",
    "capacity": 300,
    "location": "Floor 1",
    "showtimes": []
  },
  {
    "theaterId": 5,
    "name": "3D Theater",
    "capacity": 250,
    "location": "Floor 1",
    "showtimes": []
  }
]
```

### Get Theater Showtimes (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/theaters/1/showtimes
```

**Expected Response:**
```json
[
  {
    "showtimeId": 1,
    "date": "2025-04-10",
    "time": "12:00:00",
    "movie": {
      "movieId": 1,
      "title": "Avengers: Endgame"
    },
    "theater": {
      "theaterId": 1,
      "name": "IMAX Theater"
    },
    "bookings": []
  }
]
```

## Showtime Endpoints

### Get All Showtimes

**Request:**
```
GET http://localhost:8080/api/showtimes
```

**Expected Response:**
```json
[
  {
    "showtimeId": 1,
    "date": "2025-04-10",
    "time": "12:00:00",
    "movie": {
      "movieId": 1,
      "title": "Avengers: Endgame"
    },
    "theater": {
      "theaterId": 1,
      "name": "IMAX Theater"
    },
    "bookings": []
  },
  ...
]
```

### Get Showtime by ID

**Request:**
```
GET http://localhost:8080/api/showtimes/1
```

**Expected Response:**
```json
{
  "showtimeId": 1,
  "date": "2025-04-10",
  "time": "12:00:00",
  "movie": {
    "movieId": 1,
    "title": "Avengers: Endgame"
  },
  "theater": {
    "theaterId": 1,
    "name": "IMAX Theater"
  },
  "bookings": []
}
```

### Create Showtime (Two-Table Operation)

**Request:**
```
POST http://localhost:8080/api/showtimes?movieId=1&theaterId=2
```

**Request Body:**
```json
{
  "date": "2025-05-15",
  "time": "18:30:00"
}
```

**Expected Response:**
```json
{
  "showtimeId": 6,
  "date": "2025-05-15",
  "time": "18:30:00",
  "movie": {
    "movieId": 1,
    "title": "Avengers: Endgame"
  },
  "theater": {
    "theaterId": 2,
    "name": "Standard Theater 1"
  },
  "bookings": null
}
```

### Update Showtime

**Request:**
```
PUT http://localhost:8080/api/showtimes/6
```

**Request Body:**
```json
{
  "date": "2025-05-16",
  "time": "19:00:00"
}
```

**Expected Response:**
```json
{
  "showtimeId": 6,
  "date": "2025-05-16",
  "time": "19:00:00",
  "movie": {
    "movieId": 1,
    "title": "Avengers: Endgame"
  },
  "theater": {
    "theaterId": 2,
    "name": "Standard Theater 1"
  },
  "bookings": []
}
```

### Delete Showtime

**Request:**
```
DELETE http://localhost:8080/api/showtimes/6
```

**Expected Response:**
```
204 No Content
```

### Find Showtimes by Date

**Request:**
```
GET http://localhost:8080/api/showtimes/date/2025-04-10
```

**Expected Response:**
```json
[
  {
    "showtimeId": 1,
    "date": "2025-04-10",
    "time": "12:00:00",
    "movie": {
      "movieId": 1,
      "title": "Avengers: Endgame"
    },
    "theater": {
      "theaterId": 1,
      "name": "IMAX Theater"
    },
    "bookings": []
  }
]
```

### Get Showtime Bookings (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/showtimes/1/bookings
```

**Expected Response:**
```json
[
  {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED",
    "bookingDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    },
    "showtime": {
      "showtimeId": 1,
      "date": "2025-04-10",
      "time": "12:00:00"
    },
    "payment": null,
    "promoCode": null,
    "snackOrders": []
  }
]
```

## Booking Endpoints

### Get All Bookings

**Request:**
```
GET http://localhost:8080/api/bookings
```

**Expected Response:**
```json
[
  {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED",
    "bookingDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    },
    "showtime": {
      "showtimeId": 1,
      "date": "2025-04-10",
      "time": "12:00:00"
    },
    "payment": {
      "paymentId": 1,
      "amount": 15.99,
      "status": "COMPLETED"
    },
    "promoCode": {
      "promoCodeId": 1,
      "code": "SUMMER10",
      "discount": 10.0
    },
    "snackOrders": []
  },
  ...
]
```

### Get Booking by ID

**Request:**
```
GET http://localhost:8080/api/bookings/1
```

**Expected Response:**
```json
{
  "bookingId": 1,
  "seatNumber": "A1",
  "status": "CONFIRMED",
  "bookingDate": "2025-04-10",
  "user": {
    "userId": 1,
    "name": "User 1"
  },
  "showtime": {
    "showtimeId": 1,
    "date": "2025-04-10",
    "time": "12:00:00"
  },
  "payment": {
    "paymentId": 1,
    "amount": 15.99,
    "status": "COMPLETED"
  },
  "promoCode": {
    "promoCodeId": 1,
    "code": "SUMMER10",
    "discount": 10.0
  },
  "snackOrders": []
}
```

### Create Booking (Two-Table Operation)

**Request:**
```
POST http://localhost:8080/api/bookings?userId=1&showtimeId=2
```

**Request Body:**
```json
{
  "seatNumber": "F12"
}
```

**Expected Response:**
```json
{
  "bookingId": 6,
  "seatNumber": "F12",
  "status": "PENDING",
  "bookingDate": "2025-04-10",
  "user": {
    "userId": 1,
    "name": "User 1"
  },
  "showtime": {
    "showtimeId": 2,
    "date": "2025-04-11",
    "time": "13:00:00"
  },
  "payment": null,
  "promoCode": null,
  "snackOrders": null
}
```

### Update Booking

**Request:**
```
PUT http://localhost:8080/api/bookings/6
```

**Request Body:**
```json
{
  "seatNumber": "F14",
  "status": "CONFIRMED"
}
```

**Expected Response:**
```json
{
  "bookingId": 6,
  "seatNumber": "F14",
  "status": "CONFIRMED",
  "bookingDate": "2025-04-10",
  "user": {
    "userId": 1,
    "name": "User 1"
  },
  "showtime": {
    "showtimeId": 2,
    "date": "2025-04-11",
    "time": "13:00:00"
  },
  "payment": null,
  "promoCode": null,
  "snackOrders": []
}
```

### Delete Booking

**Request:**
```
DELETE http://localhost:8080/api/bookings/6
```

**Expected Response:**
```
204 No Content
```

### Find Bookings by Status

**Request:**
```
GET http://localhost:8080/api/bookings/status/CONFIRMED
```

**Expected Response:**
```json
[
  {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED",
    "bookingDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    },
    "showtime": {
      "showtimeId": 1,
      "date": "2025-04-10",
      "time": "12:00:00"
    },
    "payment": {
      "paymentId": 1,
      "amount": 15.99,
      "status": "COMPLETED"
    },
    "promoCode": {
      "promoCodeId": 1,
      "code": "SUMMER10",
      "discount": 10.0
    },
    "snackOrders": []
  },
  ...
]
```

### Get User Bookings (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/bookings/user/1
```

**Expected Response:**
```json
[
  {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED",
    "bookingDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    },
    "showtime": {
      "showtimeId": 1,
      "date": "2025-04-10",
      "time": "12:00:00"
    },
    "payment": {
      "paymentId": 1,
      "amount": 15.99,
      "status": "COMPLETED"
    },
    "promoCode": {
      "promoCodeId": 1,
      "code": "SUMMER10",
      "discount": 10.0
    },
    "snackOrders": []
  }
]
```

### Apply Promo Code to Booking (Two-Table Operation)

**Request:**
```
PUT http://localhost:8080/api/bookings/1/apply-promo?promoCode=WELCOME20
```

**Expected Response:**
```json
{
  "bookingId": 1,
  "seatNumber": "A1",
  "status": "CONFIRMED",
  "bookingDate": "2025-04-10",
  "user": {
    "userId": 1,
    "name": "User 1"
  },
  "showtime": {
    "showtimeId": 1,
    "date": "2025-04-10",
    "time": "12:00:00"
  },
  "payment": {
    "paymentId": 1,
    "amount": 15.99,
    "status": "COMPLETED"
  },
  "promoCode": {
    "promoCodeId": 2,
    "code": "WELCOME20",
    "discount": 20.0
  },
  "snackOrders": []
}
```

## Payment Endpoints

### Get All Payments

**Request:**
```
GET http://localhost:8080/api/payments
```

**Expected Response:**
```json
[
  {
    "paymentId": 1,
    "amount": 15.99,
    "status": "COMPLETED",
    "paymentDate": null,
    "booking": {
      "bookingId": 1,
      "seatNumber": "A1",
      "status": "CONFIRMED"
    }
  },
  ...
]
```

### Get Payment by ID

**Request:**
```
GET http://localhost:8080/api/payments/1
```

**Expected Response:**
```json
{
  "paymentId": 1,
  "amount": 15.99,
  "status": "COMPLETED",
  "paymentDate": null,
  "booking": {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED"
  }
}
```

### Create Payment for Booking (Two-Table Operation)

**Request:**
```
POST http://localhost:8080/api/payments?bookingId=3
```

**Request Body:**
```json
{
  "amount": 22.99,
  "status": "COMPLETED"
}
```

**Expected Response:**
```json
{
  "paymentId": 6,
  "amount": 22.99,
  "status": "COMPLETED",
  "paymentDate": null,
  "booking": {
    "bookingId": 3,
    "seatNumber": "C5",
    "status": "CONFIRMED"
  }
}
```

### Update Payment

**Request:**
```
PUT http://localhost:8080/api/payments/6
```

**Request Body:**
```json
{
  "amount": 24.99,
  "status": "COMPLETED"
}
```

**Expected Response:**
```json
{
  "paymentId": 6,
  "amount": 24.99,
  "status": "COMPLETED",
  "paymentDate": null,
  "booking": {
    "bookingId": 3,
    "seatNumber": "C5",
    "status": "CONFIRMED"
  }
}
```

### Delete Payment

**Request:**
```
DELETE http://localhost:8080/api/payments/6
```

**Expected Response:**
```
204 No Content
```

### Find Payments by Status

**Request:**
```
GET http://localhost:8080/api/payments/status/COMPLETED
```

**Expected Response:**
```json
[
  {
    "paymentId": 1,
    "amount": 15.99,
    "status": "COMPLETED",
    "paymentDate": null,
    "booking": {
      "bookingId": 1,
      "seatNumber": "A1",
      "status": "CONFIRMED"
    }
  },
  ...
]
```

### Get Payment for Booking (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/payments/booking/1
```

**Expected Response:**
```json
{
  "paymentId": 1,
  "amount": 15.99,
  "status": "COMPLETED",
  "paymentDate": null,
  "booking": {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED"
  }
}
```

## Review Endpoints

### Get All Reviews

**Request:**
```
GET http://localhost:8080/api/reviews
```

**Expected Response:**
```json
[
  {
    "reviewId": 1,
    "rating": 4.5,
    "reviewText": "Great movie, loved the special effects!",
    "reviewDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    },
    "movie": {
      "movieId": 1,
      "title": "Avengers: Endgame"
    }
  },
  ...
]
```

### Get Review by ID

**Request:**
```
GET http://localhost:8080/api/reviews/1
```

**Expected Response:**
```json
{
  "reviewId": 1,
  "rating": 4.5,
  "reviewText": "Great movie, loved the special effects!",
  "reviewDate": "2025-04-10",
  "user": {
    "userId": 1,
    "name": "User 1"
  },
  "movie": {
    "movieId": 1,
    "title": "Avengers: Endgame"
  }
}
```

### Create Review (Two-Table Operation)

**Request:**
```
POST http://localhost:8080/api/reviews?userId=2&movieId=3
```

**Request Body:**
```json
{
  "rating": 4.2,
  "reviewText": "Mind-bending plot and amazing visuals!"
}
```

**Expected Response:**
```json
{
  "reviewId": 6,
  "rating": 4.2,
  "reviewText": "Mind-bending plot and amazing visuals!",
  "reviewDate": "2025-04-10",
  "user": {
    "userId": 2,
    "name": "User 2"
  },
  "movie": {
    "movieId": 3,
    "title": "Inception"
  }
}
```

### Update Review

**Request:**
```
PUT http://localhost:8080/api/reviews/6
```

**Request Body:**
```json
{
  "rating": 4.5,
  "reviewText": "Mind-bending plot and amazing visuals! One of my favorites."
}
```

**Expected Response:**
```json
{
  "reviewId": 6,
  "rating": 4.5,
  "reviewText": "Mind-bending plot and amazing visuals! One of my favorites.",
  "reviewDate": "2025-04-10",
  "user": {
    "userId": 2,
    "name": "User 2"
  },
  "movie": {
    "movieId": 3,
    "title": "Inception"
  }
}
```

### Delete Review

**Request:**
```
DELETE http://localhost:8080/api/reviews/6
```

**Expected Response:**
```
204 No Content
```

### Find Reviews by Rating

**Request:**
```
GET http://localhost:8080/api/reviews/rating/4.5
```

**Expected Response:**
```json
[
  {
    "reviewId": 1,
    "rating": 4.5,
    "reviewText": "Great movie, loved the special effects!",
    "reviewDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    },
    "movie": {
      "movieId": 1,
      "title": "Avengers: Endgame"
    }
  },
  ...
]
```

## Notification Endpoints

### Get All Notifications

**Request:**
```
GET http://localhost:8080/api/notifications
```

**Expected Response:**
```json
[
  {
    "notificationId": 1,
    "type": "BOOKING",
    "message": "Your booking has been confirmed.",
    "sentDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    }
  },
  ...
]
```

### Get Notification by ID

**Request:**
```
GET http://localhost:8080/api/notifications/1
```

**Expected Response:**
```json
{
  "notificationId": 1,
  "type": "BOOKING",
  "message": "Your booking has been confirmed.",
  "sentDate": "2025-04-10",
  "user": {
    "userId": 1,
    "name": "User 1"
  }
}
```

### Create Notification for User (Two-Table Operation)

**Request:**
```
POST http://localhost:8080/api/notifications?userId=3
```

**Request Body:**
```json
{
  "type": "SPECIAL_OFFER",
  "message": "Special discount on all movies this weekend!"
}
```

**Expected Response:**
```json
{
  "notificationId": 6,
  "type": "SPECIAL_OFFER",
  "message": "Special discount on all movies this weekend!",
  "sentDate": "2025-04-10",
  "user": {
    "userId": 3,
    "name": "User 3"
  }
}
```

### Update Notification

**Request:**
```
PUT http://localhost:8080/api/notifications/6
```

**Request Body:**
```json
{
  "type": "SPECIAL_OFFER",
  "message": "Special discount on all movies this weekend! Use code WEEKEND50."
}
```

**Expected Response:**
```json
{
  "notificationId": 6,
  "type": "SPECIAL_OFFER",
  "message": "Special discount on all movies this weekend! Use code WEEKEND50.",
  "sentDate": "2025-04-10",
  "user": {
    "userId": 3,
    "name": "User 3"
  }
}
```

### Delete Notification

**Request:**
```
DELETE http://localhost:8080/api/notifications/6
```

**Expected Response:**
```
204 No Content
```

### Get User Notifications (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/notifications/user/1
```

**Expected Response:**
```json
[
  {
    "notificationId": 1,
    "type": "BOOKING",
    "message": "Your booking has been confirmed.",
    "sentDate": "2025-04-10",
    "user": {
      "userId": 1,
      "name": "User 1"
    }
  }
]
```

## PromoCode Endpoints

### Get All Promo Codes

**Request:**
```
GET http://localhost:8080/api/promocodes
```

**Expected Response:**
```json
[
  {
    "promoCodeId": 1,
    "code": "SUMMER10",
    "discount": 10.0,
    "expiryDate": "2025-05-10",
    "bookings": []
  },
  ...
]
```

### Get Promo Code by ID

**Request:**
```
GET http://localhost:8080/api/promocodes/1
```

**Expected Response:**
```json
{
  "promoCodeId": 1,
  "code": "SUMMER10",
  "discount": 10.0,
  "expiryDate": "2025-05-10",
  "bookings": []
}
```

### Create Promo Code

**Request:**
```
POST http://localhost:8080/api/promocodes
```

**Request Body:**
```json
{
  "code": "NEWUSER30",
  "discount": 30.0,
  "expiryDate": "2025-07-15"
}
```

**Expected Response:**
```json
{
  "promoCodeId": 6,
  "code": "NEWUSER30",
  "discount": 30.0,
  "expiryDate": "2025-07-15",
  "bookings": null
}
```

### Update Promo Code

**Request:**
```
PUT http://localhost:8080/api/promocodes/6
```

**Request Body:**
```json
{
  "code": "NEWUSER30",
  "discount": 35.0,
  "expiryDate": "2025-08-15"
}
```

**Expected Response:**
```json
{
  "promoCodeId": 6,
  "code": "NEWUSER30",
  "discount": 35.0,
  "expiryDate": "2025-08-15",
  "bookings": []
}
```

### Delete Promo Code

**Request:**
```
DELETE http://localhost:8080/api/promocodes/6
```

**Expected Response:**
```
204 No Content
```

### Find Promo Code by Code

**Request:**
```
GET http://localhost:8080/api/promocodes/code/SUMMER10
```

**Expected Response:**
```json
{
  "promoCodeId": 1,
  "code": "SUMMER10",
  "discount": 10.0,
  "expiryDate": "2025-05-10",
  "bookings": []
}
```

### Find Valid Promo Codes

**Request:**
```
GET http://localhost:8080/api/promocodes/valid
```

**Expected Response:**
```json
[
  {
    "promoCodeId": 1,
    "code": "SUMMER10",
    "discount": 10.0,
    "expiryDate": "2025-05-10",
    "bookings": []
  },
  ...
]
```

## Snack Endpoints

### Get All Snacks

**Request:**
```
GET http://localhost:8080/api/snacks
```

**Expected Response:**
```json
[
  {
    "snackId": 1,
    "name": "Popcorn",
    "price": 5.99,
    "availability": true,
    "snackOrders": []
  },
  ...
]
```

### Get Snack by ID

**Request:**
```
GET http://localhost:8080/api/snacks/1
```

**Expected Response:**
```json
{
  "snackId": 1,
  "name": "Popcorn",
  "price": 5.99,
  "availability": true,
  "snackOrders": []
}
```

### Create Snack

**Request:**
```
POST http://localhost:8080/api/snacks
```

**Request Body:**
```json
{
  "name": "Ice Cream",
  "price": 4.49,
  "availability": true
}
```

**Expected Response:**
```json
{
  "snackId": 6,
  "name": "Ice Cream",
  "price": 4.49,
  "availability": true,
  "snackOrders": null
}
```

### Update Snack

**Request:**
```
PUT http://localhost:8080/api/snacks/6
```

**Request Body:**
```json
{
  "name": "Ice Cream",
  "price": 4.99,
  "availability": true
}
```

**Expected Response:**
```json
{
  "snackId": 6,
  "name": "Ice Cream",
  "price": 4.99,
  "availability": true,
  "snackOrders": []
}
```

### Delete Snack

**Request:**
```
DELETE http://localhost:8080/api/snacks/6
```

**Expected Response:**
```
204 No Content
```

### Find Snacks by Availability

**Request:**
```
GET http://localhost:8080/api/snacks/available/true
```

**Expected Response:**
```json
[
  {
    "snackId": 1,
    "name": "Popcorn",
    "price": 5.99,
    "availability": true,
    "snackOrders": []
  },
  ...
]
```

### Find Snacks by Price Less Than

**Request:**
```
GET http://localhost:8080/api/snacks/price/5.0
```

**Expected Response:**
```json
[
  {
    "snackId": 2,
    "name": "Soda",
    "price": 3.99,
    "availability": true,
    "snackOrders": []
  },
  {
    "snackId": 4,
    "name": "Candy",
    "price": 4.99,
    "availability": true,
    "snackOrders": []
  }
]
```

## SnackOrder Endpoints

### Get All Snack Orders

**Request:**
```
GET http://localhost:8080/api/snackorders
```

**Expected Response:**
```json
[
  {
    "snackOrderId": 1,
    "quantity": 2,
    "booking": {
      "bookingId": 1,
      "seatNumber": "A1",
      "status": "CONFIRMED"
    },
    "snack": {
      "snackId": 1,
      "name": "Popcorn",
      "price": 5.99
    }
  },
  ...
]
```

### Get Snack Order by ID

**Request:**
```
GET http://localhost:8080/api/snackorders/1
```

**Expected Response:**
```json
{
  "snackOrderId": 1,
  "quantity": 2,
  "booking": {
    "bookingId": 1,
    "seatNumber": "A1",
    "status": "CONFIRMED"
  },
  "snack": {
    "snackId": 1,
    "name": "Popcorn",
    "price": 5.99
  }
}
```

### Create Snack Order (Two-Table Operation)

**Request:**
```
POST http://localhost:8080/api/snackorders?bookingId=2&snackId=3
```

**Request Body:**
```json
{
  "quantity": 2
}
```

**Expected Response:**
```json
{
  "snackOrderId": 6,
  "quantity": 2,
  "booking": {
    "bookingId": 2,
    "seatNumber": "B3",
    "status": "CONFIRMED"
  },
  "snack": {
    "snackId": 3,
    "name": "Nachos",
    "price": 6.99
  }
}
```

### Update Snack Order

**Request:**
```
PUT http://localhost:8080/api/snackorders/6
```

**Request Body:**
```json
{
  "quantity": 3
}
```

**Expected Response:**
```json
{
  "snackOrderId": 6,
  "quantity": 3,
  "booking": {
    "bookingId": 2,
    "seatNumber": "B3",
    "status": "CONFIRMED"
  },
  "snack": {
    "snackId": 3,
    "name": "Nachos",
    "price": 6.99
  }
}
```

### Delete Snack Order

**Request:**
```
DELETE http://localhost:8080/api/snackorders/6
```

**Expected Response:**
```
204 No Content
```

### Get Booking Snack Orders (Two-Table Operation)

**Request:**
```
GET http://localhost:8080/api/snackorders/booking/1
```

**Expected Response:**
```json
[
  {
    "snackOrderId": 1,
    "quantity": 2,
    "booking": {
      "bookingId": 1,
      "seatNumber": "A1",
      "status": "CONFIRMED"
    },
    "snack": {
      "snackId": 1,
      "name": "Popcorn",
      "price": 5.99
    }
  }
]
```

## Testing Instructions

To test these endpoints, follow these steps:

1. Start the Spring Boot application
2. Use Postman or a similar tool to send requests to the endpoints
3. First, populate the database using the `/api/data/populate` endpoint
4. Test each endpoint with the provided request and verify the response matches the expected format
5. Document each test with screenshots and the JSON used for requests and responses

For Postman testing:
1. Create a new collection for the Cinema Booking App
2. Add a request for each endpoint
3. Set the appropriate HTTP method and URL
4. For POST and PUT requests, add the request body in JSON format
5. Send the request and verify the response
6. Save the request and response for documentation

## Conclusion

This documentation provides comprehensive testing instructions for all REST endpoints implemented in the Cinema Booking Application. By following these instructions, you can verify that all endpoints are working correctly and meet the requirements of the assignment.
