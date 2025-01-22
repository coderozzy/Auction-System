# Auction System

# This project was done by Oguzhan Turksoy(studentid:55032) for the class cloud-oriented web applications group L2

A web-based auction system built with Spring Boot that allows users to create, manage, and participate in auctions.

## Features

- User Authentication & Authorization
- Auction Management (Create, View, Update, Delete)
- Real-time Bidding System
- Secure Payment Integration
- Auction Information Download

## Technologies

- Java 17
- Spring Boot 3.3.0
- H2 Database
- Thymeleaf
- Bootstrap 5
- Docker

## Prerequisites

- Java Development Kit (JDK) 17
- Maven
- Docker (optional)

## Getting Started

### Option 1: Running with Maven

1. Clone the repository
```bash
git clone [repository-url]
```

2. Navigate to project directory
```bash
cd Auction-System
```

3. Run the application
```bash
./mvnw spring-boot:run
```

### Option 2: Running with Docker

1. Build and run using Docker Compose
```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`

## Default Accounts

The system comes with pre-configured accounts for testing:

```
Admin Account:
Username: admin
Password: 1

Test User Accounts:
Username: rainbowDash
Password: sunshine5

Username: whisperingWillow
Password: willow789

Username: midnightRider
Password: midnight234
```

## Database Configuration

The application uses H2 in-memory database with the following configuration:

- Console URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:auctiondb`
- Username: sa
- Password: password

## Project Structure

```
Auction-System/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/auction/system/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       ├── application.properties
│   │       └── data.sql
├── docker-compose.yml
└── Dockerfile
```
