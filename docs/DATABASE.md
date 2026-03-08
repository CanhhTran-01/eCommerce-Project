### Tables
account
users
account_roles
category
product
pro_thumbnail_images
cart
cart_item
order
order_item
payment
review
wishlist
invalid_token

---

### Relationships
account (1) ─── (1) users

account (1) ─── (n) account_roles

category (1) ─── (n) product

product (1) ─── (n) pro_thumbnail_images

users (1) ─── (n) cart  
cart (1) ─── (n) cart_item

users (1) ─── (n) order
order (1) ─── (n) order_item
order_item (n) ─── (1) product

order (1) ─── (1) payment

users (1) ─── (n) review
review (n) ─── (1) product

users (n) ─── (n) product  (wishlist)


### Note
A user can have multiple carts to preserve cart history
and track abandoned carts. Only one cart should have status = ACTIVE. (planned for future development)

