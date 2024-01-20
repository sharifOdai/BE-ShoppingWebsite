#Shopping Website Backend 
Introduction
Welcome to the backend documentation for my shopping website. This backend is built using Java and the Spring Boot framework, with an H2 database for data storage.

Table of Contents
Project Structure
Database Schema
API Endpoints
Authentication
Order Management
Stock Management
Favorite List
User Management
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
1. Project Structure
Our project follows the MVC (Model-View-Controller) architecture and is organized as follows:

src/main/java/com.example.shopping: Contains the main application class and configuration files.
src/main/java/com.example.shopping.controller: Controllers responsible for handling incoming HTTP requests.
src/main/java/com.example.shopping.model: Entity classes representing database tables.
src/main/java/com.example.shopping.repository: Interfaces extending Spring Data JPA repositories for database operations.
src/main/java/com.example.shopping.service: Service classes containing business logic.
src/main/resources: Configuration files, application properties, and database schema scripts.
2. Database Schema
The database schema includes tables for Users, Items, Orders, and Favorites. Refer to the schema scripts in the src/main/resources directory for details.

3. API Endpoints
Our backend provides the following API endpoints:

/api/items: Get a list of all available items.
/api/items/search?name={itemName}: Search for items by name.
/api/users: Create a new user.
/api/users/{userId}: Get user details, update user information, or delete the user.
/api/orders: Get a list of orders for the logged-in user.
/api/orders/{orderId}: Get details of a specific order, update order items, or proceed to payment.

4. Authentication
Authentication is handled using a simple username and password mechanism. Users can create an account, log in, and log out.

5. Order Management
Orders are divided into two types: temporary (pending) and closed. The user can add or remove items from a pending order, proceed to payment, and view details of closed orders.

6. Stock Management
The stock for each item is tracked, and users cannot order more items than are available in stock. When an item is out of stock, it is clearly indicated on the frontend.

7. Favorite List
Users can create a favorite list, adding and removing items. The favorite list is persistent across logins and is accessible only when the user is logged in.

8. User Management
Users have the ability to update their information, delete their account, and manage their favorite items and orders.

Conclusion
This README provides an overview of the backend structure, database schema, API endpoints, and key features of our shopping website. For detailed information on each component, refer to the corresponding classes and scripts in the source code.
