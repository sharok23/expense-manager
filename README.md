# Expense Manager
This project provides a RESTful API for an Expense Manager application. This repository contains a Spring Boot application that manages transaction and category. The application includes two main controllers:
TransactionController, which handle HTTP requests for creating, retrieving, and deleting transaction. The TransactionController also supports retrieving transaction with associated category colors.
CategoryController, which handle HTTP requests for retrieving category.
The application’s logic is encapsulated in two service classes:
CategoryService and TransactionService. These services interact with the CategoryRepository and TransactionRepository to persist and retrieve data from the database. Unit tests for these controllers and services are included to ensure the functionality and reliability of the application.

# CategoryController

Base URL: /v1/category

GET / : Get a list of all category. It returns a list of CategoryResponse.

# TransactionController

Base URL: /v1/transaction

POST / : Create a new transaction. It takes a TransactionRequest as input and returns a TransactionResponse.

DELETE /{id} : Delete a transaction by its ID. It takes an ID as a path variable and returns a string message.

GET /labels : Get a list of all transactions with color.

GET /{date} : Get a list of transactions based on given date.

# UserController

Base URL: /v1/user

POST /signup : Register a new user. Accepts a SignupRequest and returns a SignupResponse.

POST /login : Authenticate a user. Accepts a LoginRequest and returns a LoginResponse on successful login.

