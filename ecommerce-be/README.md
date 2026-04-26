# eCommerce Backend

Backend service for the eCommerce project built with **Spring Boot**.

Provides 40+ REST APIs covering authentication, product browsing, cart management, order processing, and more.

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA + Hibernate
- MySQL + Redis
- JWT + OAuth2 (Google, Facebook)
- Cloudinary (image storage)
- MapStruct (DTO mapping)
- Swagger UI (API docs)
- JUnit + Mockito (testing)
- Docker + Docker Compose

---

## Architecture

The project follows **Layered Architecture** inside a **Monolith Spring Boot application**.

```
Controller → Service → Repository → Database
```

| Layer | Responsibility |
|---|---|
| Controller | Handle HTTP requests, route to service |
| Service | Business logic |
| Repository | Data access (JPA + custom queries) |
| DTO | Request / Response models |
| Entity | Database mapping |
| Security | JWT, OAuth2, filters, auth handlers |
| Mapper | Entity ↔ DTO (MapStruct) |
| Validator | Custom input validation |
| Exception | Global exception handling |

---

## Folder Structure

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
    │       ├── enums                # Enum definitions (ErrorCode, Role, ...)
    │       ├── exception            # Global exception handling
    │       ├── mapper               # Entity ↔ DTO mapping (MapStruct)
    │       ├── repository
    │       │   └── custom           # Custom repository (product filter, search)
    │       ├── security             # Security config, JWT, OAuth2, auth handlers
    │       ├── service              # Business logic layer
    │       ├── utils                # Utility classes
    │       ├── validator            # Custom validators
    │       └── EcommerceBeApplication
    │
    └── resources
        ├── application.yaml
        ├── application-dev.yaml
        └── application-prod.yaml
```

---

## Main Features

### Authentication & Security

- Register / Login (Local + Google + Facebook via OAuth2)
- Account merging — link OAuth2 account to existing local account (same email, unique constraint)
- JWT with **refresh token rotation** (single token pattern: access + refresh combined, stored invalid tokens in Redis with expiry)
- Bcrypt password encoding
- Forgot password via OTP: generate → send via email → store in Redis with TTL
- **Rate limiting** (Redis-backed) against brute force:
  - `generateOTP()`: max 5 OTP requests; max 3 wrong OTP attempts
  - `login()`: max 5 wrong password attempts → temporary account lock (15 minutes)
- RBAC with 4 roles: `guest`, `user`, `admin`, `seller`
- Method-level security with `@PreAuthorize`
- Exception handling at Spring Security filter layer (standardized error response)

### Product

- Get products on sale
- Get categories
- Get products by category
- Product detail + images
- Filter & search products (name, category, price range, sorting) via custom repository
- Product suggestions (Redis ZSET — ranked by search frequency)

### Cart

Cart is stored in **Redis (Hash + TTL)**:

- Add item
- Remove item
- View cart

### Order

- Place order — protected with **Pessimistic Lock** to prevent overselling under concurrent requests
- Order history
- Order detail (per product)
- Pending/undelivered orders

### Wishlist

- Add to wishlist
- Remove from wishlist
- Check if a product is in the current user's wishlist

### Review

- Add review
- Get reviews by product

### User

- View profile
- Update profile
- Change password
- Upload avatar (Cloudinary)
- Purchase history

### Search

- Product search with filter criteria
- Search keyword suggestions ranked by popularity (Redis Sorted Set — ZSET)

### Media

Images are uploaded to **Cloudinary**. The database stores only the URL:

- User avatar
- Product images
- Category images

---

## API & Response Standards

- Standardized response: `ApiResponse<T>` (Java generic) + `ResponseEntity`
- Global exception handler: `@RestControllerAdvice` + `BaseException` + `ErrorCode` enums
- Input validation on all request DTOs
- Custom validator: date of birth (minimum age 15)
- Swagger UI with controller-grouped documentation

---

## Database

**MySQL** (relational) + **Redis** (cache & session data)

### Tables & Relationships

```
accounts      (1) ─── (1)  users
accounts      (1) ─── (n)  account_roles
categories    (1) ─── (n)  products
products      (1) ─── (n)  pro_thumbnail_images
users         (1) ─── (1)  cart
cart          (1) ─── (n)  cart_items
users         (1) ─── (n)  orders
orders        (1) ─── (n)  order_items
order_items   (n) ─── (1)  products
orders        (1) ─── (1)  payment
users         (1) ─── (n)  reviews
reviews       (n) ─── (1)  products
users         (n) ─── (n)  products  (wishlist)
```

---

## Performance & Query Optimization

- `FetchType` tuned per entity (LAZY/EAGER) to avoid unnecessary queries and N+1 risks
- Batch fetch + map lookup pattern in `getCartItems()`, `createOrder()`, `uploadProductImages()` to eliminate N+1 queries
- Query count verified with **Hibernate Statistics**
- `@Query` with JOIN for complex queries returning DTO directly at repository layer
- **Pessimistic Lock** on inventory update to handle concurrent order placement (M users ordering same product with N stock, M > N)

---

## Testing

### Unit Tests (JUnit + Mockito)

| Class | Test Cases |
|---|---|
| `AccountService.register()` | success, duplicate username, duplicate local email, merge OAuth2 into local |
| `AuthenticationService.login()` | success, account not found, wrong password |
| `AuthenticationService.refreshToken()` | success, expired token, invalid token |
| `JwtHandlerComponent` | valid claims, expired token, wrong signature |

### Integration Tests (H2 in-memory database)

| Endpoint | Test Cases |
|---|---|
| `POST /login` | success, wrong username, wrong password |
| `GET /users` | admin → list returned, user → 403, no token → 401 |

---

## Docker

### Run with Docker Compose (recommended)

```bash
docker compose up -d
```

Docker Compose handles: network creation, container startup order, health checks (waits for MySQL before starting backend), and persistent volumes.

### Manual Docker Setup

1. **Build backend image**

```bash
./mvnw package -DskipTests
docker build -t ecommerce-backend:1.0 .
```

2. **Create Docker network**

```bash
docker network create ecommerce-net
```

3. **Run MySQL**

```bash
docker run -d --name ecom-db-internal --network ecommerce-net \
  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=your_password mysql:8.0.36
```

```sql
-- Connect and create database
docker exec -it ecom-db-internal mysql -u root -p
CREATE DATABASE ecommerce_db_docker;
```

4. **Run Redis**

```bash
docker run -d --name ecom-redis-internal --network ecommerce-net \
  -p 6379:6379 redis
```

5. **Run Backend**

```bash
docker run -d --name ecom-be-internal --network ecommerce-net \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://ecom-db-internal:3306/ecommerce_db_docker \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  ecommerce-backend:1.0
```

### Docker Architecture

```
Docker Network: ecommerce-net
│
├── MySQL Container      (ecom-db-internal)   port 3306
├── Redis Container      (ecom-redis-internal) port 6379
└── Backend Container    (ecom-be-internal)    port 8080
```

---

## Run Locally (without Docker)

Requirements: Java 21, MySQL, Redis

```bash
cd ecommerce-be
./mvnw spring-boot:run
```

---

## API Documentation

Swagger UI is available after starting the application:

```
http://localhost:8080/eCommerce/swagger-ui/index.html
```

Test endpoints directly:

```
http://localhost:8080/eCommerce/api/products
```
