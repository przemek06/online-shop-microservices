# Online shop microservices
Simple online shop created in microservices architecture.

## Setup
To run this app you need Docker installed. If you have it, you need to fill information about you email in docker-compose.yml file. Then, you just need to run this command (on Windows) in project's root directory:
```
build_and_deploy
```

## Technologies
* Eureka
* API Gateway
* OpenFeign
* Cloud Stream with RabbitMQ binder
* JPA
* Security
* Web

## Features
* Users and admin can login with username and password
* Admins can add and delete products
* Users can lists all the products and make orders
* After an successful order, it will be saved, products updated and email sent to the buyer
* Admin can list all the orders and ordered item, and delete them

### To do
* There is a problem with files in notification-service, which causes it to not work correctly.
