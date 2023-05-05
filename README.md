# LPMS - Licence Plate Management System App

The Licence Plate Management System App is a Spring Boot application that provides an API for managing custom license plates. Users can purchase, search, and view the details of available license plates. The application validates the license plates based on their format and checks for inappropriate words. It also allows registration of new users.

### Link to video demo - https://youtu.be/WIb0jMb4p_k

## Features

1. Purchase license plates with valid formats and without inappropriate words.
2. Search for available license plates using a pattern with optional wildcard characters.
3. Retrieve license plate information by plate number.
4. Register new users.

## How It Works

The application has three main components:

1. **Controllers**: The `LicensePlateController` handles requests related to license plates, such as purchasing, searching, and retrieving license plate information. The `RegistrationController` handles user registration.
2. **Services**: The `LicensePlateService`, `UserService`, and `OwnershipLogService` handle the core business logic related to license plates, users, and ownership logs, respectively.
3. **Repositories**: The `LicensePlateRepository`, `UserRepository`, and `OwnershipLogRepository` handle database operations for license plates, users, and ownership logs, respectively.

When a user sends an API request to purchase, search, or retrieve license plate information, the request is processed by the corresponding controller. The controller calls the appropriate service to perform the required operations. The service, in turn, interacts with the repository to perform database operations.

## How to Use

1. Clone the repository.
2. Ensure that you have a PostgreSQL server running with the required database and user credentials as specified in the `application.properties` file.
3. Import the project into your favorite IDE and run the `LicencePlateManagementSystemApp` class.
4. Use a REST client or tool, such as Postman or curl, to send requests to the exposed API endpoints.

### API Endpoints

- **Purchase License Plate**: `POST /purchase`

  Payload: `{"plateNumber": "XX99XXX", "price": 1000, "firstName": "John", "lastName": "Doe", "email": "john.doe@example.com"}`

- **Get License Plate By Number**: `GET /license-plates/{plateNumber}`

  Example: `/license-plates/AB123CD`

- **Search License Plates By Pattern**: `GET /license-plates/search/{plateNumber}`

  Example: `/license-plates/search/A3*D`

- **Register New User**: `POST /registration/register`

  Payload: `{"firstName": "John", "lastName": "Doe", "email": "john.doe@example.com"}`



### React Front-end:

The front-end of the License Plate Management System is built using React, a popular JavaScript library for building user interfaces. React's component-based architecture allows for a modular approach to the development process. The state management is handled using React's built-in state system, which ensures that the user interface remains in sync with the underlying data.

The front-end application uses Axios, a popular promise-based HTTP client for the browser, to communicate with the back-end services. Axios facilitates making HTTP requests to the server and handling the responses, making it easier to interact with the back-end services and manage the data flow within the application.

### Node.js Service:

The Node.js service, in this case, is specifically designed to generate random license plate numbers based on a search pattern provided by the user. The service is built using Express, a popular web application framework for Node.js. Express provides a set of features for building web applications, such as routing, middleware support, and request/response handling.

The Node.js service also handles Cross-Origin Resource Sharing (CORS) configuration, ensuring that the front-end can communicate with the service without any security issues.

The main functionality of the service is exposed through the following endpoint:

GET /license-plates/search/:plateNumber: This endpoint takes the user's input as a parameter and generates a list of random license plate numbers based on the input pattern. If the input contains a wildcard character (*), the service generates plate numbers accordingly. Otherwise, the service generates random plates with varying lengths.

### Spring Boot Back-end:

The back-end of the License Plate Management System is built using Spring Boot, a widely used Java-based framework that simplifies the development of stand-alone, production-grade applications. Spring Boot provides a set of default configurations and conventions, allowing developers to focus on building the application's functionality.

The back-end is responsible for managing the data and business logic of the application. It provides various RESTful endpoints for the front-end to interact with, including:

POST /purchase: This endpoint is responsible for purchasing a license plate. It validates the plate number format, checks for inappropriate words, and verifies that the plate number is available. Upon successful validation, it creates or retrieves the corresponding user and completes the purchase process.

GET /license-plates/{plateNumber}: This endpoint returns the license plate information based on the provided plate number. If the license plate does not exist, it generates a new one and returns the information.

GET /license-plates/search/{plateNumber}: This endpoint searches for license plates based on a pattern provided by the user. If the pattern contains a wildcard character, it generates random license plates based on the search pattern. If the pattern does not contain any wildcards, it returns the information for the available license plate.

POST /registration/register: This endpoint is responsible for registering a new user in the system. It takes user information as input and registers the user.

The back-end uses the PostgreSQL database to store the data, with Spring Boot's Data JPA module handling the database operations. The application is also configured to expose the necessary entity IDs, enabling the front-end to identify and manage the entities.


In the Spring Boot application, the CORS configuration is handled using a REST configuration class. The class includes a custom CORS configuration bean, which allows for fine-tuning the CORS settings. This ensures that the front-end can communicate with the back-end services without any security issues.

### Docker files

Docker has been employed to containerize the License Plate Management System for consistent deployment across platforms. Each component has a Docker configuration:

Node.js Service: Utilizes the official Node.js v14 image, installs dependencies, copies source code, exposes port 3001, and starts the server.

React Client: Similar to Node.js Service, but exposes port 3000 for the React app.

Spring Boot Application: Uses OpenJDK 19 JDK slim image, copies the JAR file, exposes port 8080, and starts the Java application.

Docker Compose: Orchestrates services, such as the PostgreSQL database, Spring Boot app, Node.js proxy, and React client, defining dependencies and environment variables, and mapping ports for smooth interaction.