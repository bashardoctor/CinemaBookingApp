# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /build
COPY . .
RUN mvn -f CinemaBookingApp/pom.xml clean package -DskipTests

# Step 2: Run the application
FROM openjdk:17
WORKDIR /app
COPY --from=build /build/CinemaBookingApp/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
