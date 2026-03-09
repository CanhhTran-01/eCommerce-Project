# eCommerce Backend

Backend service for the eCommerce project built with **Spring Boot**.

The backend provides REST APIs for authentication, product browsing, cart management, and order processing.

---

# Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Redis
- JWT Authentication
- OAuth2 (Google, Facebook)
- Cloudinary

---

# Architecture

The project follows **Layered Architecture**.

Controller → Service → Repository → Database

Main layers:

- **Controller** – handle incoming HTTP requests
- **Service** – business logic
- **Repository** – data access layer
- **DTO** – request/response models
- **Entity** – database mapping

---

# Folder Structure

```
src
└── main
    ├── java
    │   └── com.myproject.ecommerce
    │       ├── configuration        # Spring configuration classes
    │       ├── controller           # REST controllers (API endpoints)
    │       ├── dto
    │       │   ├── request          # Request DTOs
    │       │   └── response         # Response DTOs
    │       ├── entity               # JPA entities
    │       ├── enums                # Enum definitions
    │       ├── exception            # Global exception handling
    │       ├── mapper               # Entity ↔ DTO mapping
    │       ├── repository
    │       │   └── custom           # Custom repository (product filter/search)
    │       ├── security             # Security configs, JWT, auth handlers
    │       ├── service              # Business logic layer
    │       ├── utils                # Utility classes
    │       ├── validator            # Custom validations
    │       └── EcommerceBeApplication
    │
    └── resources
        ├── static
        ├── templates
        ├── application.yaml
        ├── application-dev.yaml
        └── application-prod.yaml
```

---

# Main Features

### Authentication

- Register / Login
- Google login
- Facebook login
- JWT authentication

### User

- Update profile
- Change password
- Forgot password using OTP (Redis)

### Product

- Get products on sale
- Get categories
- Filter & search products
- Product detail

### Cart

Cart is stored in **Redis**.

- Add item
- Remove item
- View cart

### Order

- Place order
- Order history
- Order detail

### Wishlist

- Add to wishlist
- Remove from wishlist

### Review

- Add review
- View product reviews

### Search Suggestion

Redis **Sorted Set (ZSET)** is used to store search keywords and generate suggestions (both keywords and products).

---

# Image Storage

Images are uploaded to **Cloudinary**:

- user avatar
- product images
- category images

---

# Run Application

Requirements:

- Java 21
- MySQL
- Redis

Run:

```bash
  cd ecommerce-be
 ./mvnw spring-boot:run
```

---

# Run Application with Docker

The backend can also be run using Docker containers for MySQL, Redis, and the Spring Boot application.
Make sure the backend image exists:

```
docker images
```

1. Create Docker Network

```
docker network create ecommerce-net
```

2. Run MySQL Container

```
docker run -d --name ecom-db-internal --network ecommerce-net -p 3306:3306 -e MYSQL_ROOT_PASSWORD=canhtran2005 mysql:8.0.36
```

```
-- Connect to MySQL container
docker exec -it ecom-db-internal mysql -u root -p

-- then run
CREATE DATABASE ecommerce_db_docker;
```

3. Run Redis Container

```
docker run -d --name ecom-redis-internal --network ecommerce-net -p 6379:6379 redis
```

4. Run Backend Container

```
docker run -d \
--name ecom-be-internal \
--network ecommerce-net \
-p 8080:8080 \
-e SPRING_PROFILES_ACTIVE=dev \
-e SPRING_DATASOURCE_URL=jdbc:mysql://ecom-db-internal:3306/ecommerce_db_docker \
-e SPRING_DATASOURCE_USERNAME=root \
-e SPRING_DATASOURCE_PASSWORD=canhtran2005 \
ecommerce-backend:1.0
```

---

# Docker Architecture

```
Docker Network: ecommerce-net
│
├── MySQL Container
│   name: ecom-db-internal
│   port: 3306
│
├── Redis Container
│   name: ecom-redis-internal
│   port: 6379
│
└── Backend Container
    name: ecom-be-internal
    port: 8080
```

# Test Backend

```
-- All APIs are prefixed with:
http://localhost:8080/eCommerce

-- Open browser, enter url:
http://localhost:8080/eCommerce/api/products
```


