### Tables
```
accounts
users
account_roles
categories
products
pro_thumbnail_images
cart
cart_items
orders
order_items
payment
reviews
wishlist
invalid_tokens
```
---

### Relationships
```
accounts    	(1) ─── (1)  users
accounts   	    (1) ─── (n)  account_roles
categories  	(1) ─── (n)  products
products    	(1) ─── (n)  pro_thumbnail_images
users       	(1) ─── (1)  cart  
cart       		(1) ─── (n)  cart_items
users       	(1) ─── (n)  orders
orders      	(1) ─── (n)  order_items
order_items 	(n) ─── (1)  products
orders     	    (1) ─── (1)  payment
users       	(1) ─── (n)  reviews
reviews     	(n) ─── (1)  products
users       	(n) ─── (n)  products  (wishlist)
```
