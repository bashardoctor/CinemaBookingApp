FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /build
COPY CinemaBookingApp ./CinemaBookingApp
RUN mvn -f CinemaBookingApp/pom.xml clean package -DskipTests
