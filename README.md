# User Service

## Overview
The User Service is a Spring Boot application with the purpose of conducting operations related to Users. Its version is 0.0.1-SNAPSHOT.

## Java Version
The project uses Java SDK version 17.

## Spring Boot Version
This project uses Spring Boot version 3.2.4.

## Dependencies

The project uses the following dependencies:

1. Spring Boot Starter Data JPA
2. Spring Boot Starter Web
3. Project Lombok (Optional)
4. Spring Boot Starter Test (Test Scope)
5. SpringDoc OpenAPI Starter WebMvc UI (Version 2.5.0)
6. Spring Boot Starter Actuator
7. Spring Boot Docker Compose (Version 3.1.1)
8. MySQL Connector Java (Runtime Scope)
9. Spring Boot Starter OAuth2 Client
10. Spring Boot Starter Security
11. Vaadin Spring Boot Starter
12. Spring Security Test (Test Scope)
13. Spring Boot Starter AMQP

It also uses the Vaadin BOM (Version specified: 24.3.10) in the Dependency Management section.

## Building the Project

This project makes use of the Spring Boot Maven Plugin. Lombok is excluded in the plugin configuration to avoid issues during the build.

The final name of the build output is 'app'.

## Running the Service

Follow these steps to run the User Service:

1. Build the project with Maven: mvn clean install
2. Build the Docker image: docker build -t user_service .
3. Run the service with Docker Compose: docker-compose up -d

## Testing the Service
Endpoints in the UserController class can be tested using the API key **X-API-KEY** with the value **helloToken**.
UserController Endpoints
The UserController includes the following endpoints:
1. POST /api/user: create a user
2. GET /api/user/{id}: get a user by id
3. GET /api/user: get all users
4. PUT /api/user/{id}: update a user
5. DELETE /api/user/{id}: delete a user
6. PATCH /api/user/{id}: patch a user
7. HEAD /api/user/{id}: check if a user exists
8. OPTIONS /api/user/{id}: get allowed methods
9. TRACE /api/user/{id}: echo the request 

These endpoints all utilize various methods and provide detailed responses regarding user management. The provided X-API-KEY should be included in the header of the request for these endpoints to function correctly.

## Testing Event-Driven Architecture (EDA) Feature
1. You can test the EDA feature using RabbitMQ's GUI at http://localhost:15672/#/queues with username and password which are both '**guest**'.
2. On the GUI, the **user_exchange** exchange should be created and bound to the **user_queue** queue, which should have been created when the application started.
3. To publish a message from the user_queue, use this sample message:

```
[
{
"name": "Harry",
"occupation": "BE Dev",
"age": 23
},
{
"name": "Tom",
"occupation": "Product Owner",
"age": 30
},
{
"name": "Maria",
"occupation": "UI Dev",
"age": 30
},
{
"name": "Arthur",
"occupation": "Tester",
"age": 30
},
{
"name": "Hans-Otto",
"occupation": "ECO",
"age": 30
}
]
```