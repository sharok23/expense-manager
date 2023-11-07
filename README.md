# Expense Manager
This project provides a RESTful API for an Expense Manager application. This repository contains a Spring Boot application that manages transactions and categories. The application includes two main controllers: CategoriesController and TransactionController, which handle HTTP requests for creating, retrieving, and deleting transactions and categories. The TransactionController also supports retrieving transactions with associated category colors. The application’s logic is encapsulated in two service classes: CategoriesService and TransactionService. These services interact with the CategoriesRepository and TransactionRepository to persist and retrieve data from the database. Unit tests for these controllers and services are included to ensure the functionality and reliability of the application.

# CategoriesController

Base URL: /v1/categories

POST / : Create a new category. It takes a CategoriesRequest as input and returns a CategoriesResponse.

GET / : Get a list of all categories. It returns a list of CategoriesResponse.

# TransactionController

Base URL: /v1/transaction

POST / : Create a new transaction. It takes a TransactionRequest as input and returns a TransactionResponse.

GET / : Get a list of all transactions. It returns a list of TransactionResponse.

DELETE /{id} : Delete a transaction by its ID. It takes an ID as a path variable and returns a string message.

GET /labels : Get a list of all transactions with color. It returns a list of AllTransactionResponse.
