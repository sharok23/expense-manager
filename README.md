# Expense Manager
This project provides a RESTful API for an Expense Manager application. This repository contains a Spring Boot application that manages transaction and category. The application includes two main controllers: CategoryController and TransactionController, which handle HTTP requests for creating, retrieving, and deleting transaction and category. The TransactionController also supports retrieving transaction with associated category colors. The application’s logic is encapsulated in two service classes: CategoryService and TransactionService. These services interact with the CategoryRepository and TransactionRepository to persist and retrieve data from the database. Unit tests for these controllers and services are included to ensure the functionality and reliability of the application.

# CategoriesController

Base URL: /v1/category

POST / : Create a new category. It takes a CategoryRequest as input and returns a CategoryResponse.

GET / : Get a list of all category. It returns a list of CategoryResponse.

# TransactionController

Base URL: /v1/transaction

POST / : Create a new transaction. It takes a TransactionRequest as input and returns a TransactionResponse.

GET / : Get a list of all transactions. It returns a list of TransactionResponse.

DELETE /{id} : Delete a transaction by its ID. It takes an ID as a path variable and returns a string message.

GET /labels : Get a list of all transactions with color. It returns a list of AllTransactionResponse.
