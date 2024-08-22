# Springsuit

Springsuit is a RESTful web application built using Jersey for creating and managing users, posts, and comments. This application interacts with a MySQL database using XAMPP. It provides basic CRUD operations for the resources and is documented using Swagger.

## Project Structure

- **`src/main/java`**: Contains the Java source code for the application.
  - **`com.mlindo.Springsuit`**: Base package for the project.
    - **`models`**: Contains data models for the application.
    - **`repositories`**: Contains repository classes for database interactions.
    - **`resources`**: Contains RESTful resource classes.
    - **`services`**: Contains service classes for business logic.
    - **`SwaggerConfig`**: Configures Swagger for API documentation.
    - **`MyApplication`**: Configures Jersey to scan resource classes.
- **`src/main/webapp`**: Contains web application resources.
  - **`WEB-INF`**: Configuration files for the web application.
    - **`web.xml`**: Configures the Jersey servlet.
  - **`index.jsp`**: A simple JSP page (if needed).

## Setup

### Prerequisites

1. **XAMPP**: Make sure you have XAMPP installed and running. It provides MySQL and Apache server functionalities.
2. **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.
3. **Apache Maven**: Install Maven to handle project dependencies and build.

### Configuration

1. **Database Configuration**:
   - Ensure MySQL is running on XAMPP.
   - Create a database named `my-restful-api`.
   - Import the provided SQL scripts to set up the necessary tables.
   
-- Create User Table
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(50)
);

-- Create Post Table
CREATE TABLE Posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    title VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
);

-- Create Comment Table
CREATE TABLE Comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    postId INT,
    email VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    FOREIGN KEY (postId) REFERENCES Posts(id) ON DELETE CASCADE
);
   

2. **Update Database Credentials**:
   - Update `UserRepository` and `PostRepository` classes in `src/main/java/com/mlindo/Springsuit/repositories` with your XAMPP MySQL connection details:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/my-restful-api";
     private static final String USER = "root";
     private static final String PASSWORD = ""; // Leave empty if no password
     ```

### Running the Application

1. **Build the Project**:
   ```bash
   mvn clean install

### API Endpoints
 **For Comments**:
GET /api/comments: Retrieve all comments.
GET /api/comments/{id}: Retrieve a specific comment by ID.
POST /api/comments: Create a new comment.
PUT /api/comments/{id}: Update an existing comment by ID.
DELETE /api/comments/{id}: Delete a specific comment by ID.

 **For Posts**:
GET /api/posts: Retrieve all posts.
GET /api/posts/{id}: Retrieve a specific posts by ID.
POST /api/posts: Create a new posts.
PUT /api/posts/{id}: Update an existing posts by ID.
DELETE /api/posts/{id}: Delete a specific posts by ID.

 **For User**:
GET /api/user: Retrieve all user.
GET /api/user/{id}: Retrieve a specific user by ID.
POST /api/user: Create a new user.
PUT /api/user/{id}: Update an existing user by ID.
DELETE /api/user/{id}: Delete a specific user by ID.


###Troubleshooting

**HTTP 404 Not Found**: Ensure that the context path in Tomcat matches the war file name and check the endpoint paths.

**HTTP 500 Internal Server Error**: Verify the configuration files, dependencies, and database connectivity.
###Contribution
Feel free to fork the repository and submit pull requests. If you encounter any issues, please open an issue in the repository.

###License
This project is licensed under the MIT License.
