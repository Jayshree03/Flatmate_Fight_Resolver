# Flatmate_Fight_Resolver

**Project Overview**

FlatMate Fight Resolver is a Spring Boot application designed to help flatmates manage and resolve complaints. It allows users to file complaints, vote on them, and if a complaint reaches 10+ upvotes, a fun punishment is assigned to the responsible flatmate!

**Project's Key Features**
✔ JWT-based authentication & authorization 
✔ CRUD operations for complaints 
✔ Voting system (upvote/downvote complaints) 
✔ Fun punishments for common issues 
✔ Leaderboard tracking karma points 
✔ RESTful APIs with Swagger documentation 
✔ API testing using Postman(Postman Collection)

**Tech Stack**
    Technology                              Description
1. Spring Boot	                        Backend framework
2. Spring Security	                    Authentication & Authorization
3. JWT (JSON Web Token)	                Secure API access
4. Hibernate & JPA	                    ORM for database management
5. MySQL	                            Relational Database
6. Swagger (OpenAPI)	                API Documentation
7. Postman                              API Testing
8. GitHub & Git	                        Version Control


**Setup and Installation**
1. Clone git Repository
  git clone https://github.com/Jayshree03/FlatMate-Fight-Resolver.git
  cd FlatMate-Fight-Resolver

2. Configure Database
  a. Create a MySQL database flatmate_db
  b. In resource folder, change user_name and password in application.properties
    spring.datasource.username={user_name}
    spring.datasource.password={password}

3. Access the API:
  Default server: http://localhost:8080
  Swagger UI: http://localhost:8080/swagger-ui/index.html
 
**API Endpoints** 
1. Authentication API
  a. Method : POST 
     Endpoint : /api/auth/register
  b. Method : POST
     Endpoint : /api/auth/login
   
2. Flatmates API
  a. Method : POST
     Endpoint : /api/flatmates/assign
  b. Method : GET
     Endpoint : /api/flatmates/{flatCode}/members
  c. Method : GET
     Endpoint : /api/flatmates/leaderboard

3. Voting API
  a. Method : PUT
     Endpoint : /api/complaints/{id}/vote
       
4. Complaint Management API
  a. Method : PUT
     Endpoint : /api/complaints/{id}/resolve
  b. Method : GET
     Endpoint : /api/complaints
  c. Method : POST
     Endpoint : /api/complaints
  d. Method : GET
     Endpoint : /api/complaints/trending
  e. Method : GET
     Endpoint : /api/complaints/flat/{flatcode}

**Authentication & Security**
  **JWT Authentication**: Each request to protected routes requires a valid Bearer Token.
  **Spring Security**: Ensures role-based access control.
  **CORS Configured**: Allows API access from frontend applications.
  
**How to Authenticate in Swagger**
  Login via /api/auth/login
  Copy the JWT token from the response
  Click "Authorize" in Swagger and paste Bearer <your_token>

**Swagger API Documentation**
  http://localhost:8080/v3/api-docs

**Postmant Collection**
  https://github.com/Jayshree03/Flatmate_Fight_Resolver/blob/main/Flatmate%20Fight%20Resolver.postman_collection.json

**Contributor**
  Name: Jayshree Gupta
  Contact: jayshreegupta1020@gmail.com


