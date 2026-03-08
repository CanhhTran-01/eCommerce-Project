# eCommerce Website

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3-green)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![JWT](https://img.shields.io/badge/Auth-JWT-black)
![OAuth2](https://img.shields.io/badge/Auth-OAuth2-blue)

A simple **eCommerce web application** built with **Spring Boot** and **Vanilla JavaScript**.

This project focuses mainly on **backend development**, while the frontend is a lightweight UI that consumes REST APIs from the backend.

---

## Tech Stack

### Backend
- Spring Boot
- Spring Security
- MySQL
- Redis
- JWT
- OAuth2 (Google, Facebook)
- Cloudinary

### Frontend
- HTML
- Bootstrap 5
- CSS
- Vanilla JavaScript

---

## Features

### Product
- Product browsing
- Product detail
- Product filtering (name, category, price)
- Sorting

### User
- Authentication (Local, Google, Facebook)
- Forgot password with OTP (Redis + Email)
- User profile management

### Shopping
- Shopping cart (stored in Redis)
- Order management
- Wishlist
- Product reviews

### Search
- Search suggestions
- Redis Sorted Set (ZSET) for keyword ranking

### Media
- Image upload via Cloudinary

---

## Screenshots

### Home Page

![home](docs/images/home.png)

### Product Detail

![product-detail](docs/images/product-detail.png)

### Cart

![cart](docs/images/cart.png)

### Search-filter

![search-filter](docs/images/search-filter.png)

### Profile

![profile](docs/images/profile.png)

---

## Project Structure
````
ecommerce-project
├── ecommerce-fe # Frontend (HTML + Bootstrap + JS)
└── ecommerce-be # Backend  (Spring Boot)
````


---

## Run Project

### Backend

```bash
  cd ecommerce-be
 ./mvnw spring-boot:run
```


### Frontend

Open HTML files directly or run with a local server.

---

## Author

Backend-focused learning project for practicing:

- Spring Boot
- Redis
- Authentication & Authorization
- REST API design

Currently seeking opportunities to gain real-world backend development experience.