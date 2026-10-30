# Car Spare Parts Inventory and Compatibility System

## Student Information
- **Student Name:** CYIZERE SIBORUREMA Elie
- **Student ID:** 27492
- **Course Code & Name:** INSY 8322 WEB TECHNOLOGY AND INTERNET
- **Lecturer:** Patrick DUSHIMIMANA
- **Academic Year:** 2026 - 2027

## Project Overview
The Car Spare Parts Inventory and Compatibility System is a RESTful API backend built for a spare parts shop that matches parts to car models. It manages the full lifecycle of a part order—from a customer placing an order, to a storekeeper processing it, and finally issuing the parts from stock once fulfilled.

The system handles six main entities: `Customer`, `Storekeeper`, `VehicleMake`, `StoreSection`, `PartOrder`, and `IssuedPart`.

## Technologies Used
- **Backend Framework:** Spring Boot 3.x
- **ORM / Database Access:** Spring Data JPA (Hibernate)
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Language:** Java 17

## Prerequisites
Before running this project, ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- Apache Maven
- PostgreSQL Database Server (and a GUI tool like pgAdmin)
- Postman (for testing the API endpoints)

## Database Setup
1. Open your PostgreSQL server (e.g., via pgAdmin).
2. Create a new empty database named: `car_spare_parts_db`
3. Open the project in IntelliJ IDEA.
4. Navigate to `src/main/resources/application.properties`.
5. Update the database credentials to match your local PostgreSQL setup:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/car_spare_parts_db
   spring.datasource.username=postgres
   spring.datasource.password=arsenal32
