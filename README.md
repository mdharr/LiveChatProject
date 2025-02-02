# LiveChat Project

**A Real-Time Chat Application built with Spring Boot, React, WebSockets, and MySQL.**  
This project allows users to create, join rooms, and chat in real-time. The system is designed to handle authentication, JWT token management, and real-time messaging using WebSockets.

---

## 🚀 **Table of Contents**

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Features](#features)
- [API Documentation](#api-documentation)
- [Setup Instructions](#setup-instructions)
- [Frontend Setup](#frontend-setup)
- [Backend Setup](#backend-setup)
- [Database Setup](#database-setup)
- [Testing](#testing)
- [Contributions](#contributions)
- [License](#license)

---

## 📚 **Project Overview**

This project is a full-stack **real-time chat application** with the following features:

- User authentication using JWT tokens.
- Real-time messaging with WebSocket integration.
- Room creation, joining, and private messaging functionality.
- Support for multiple chat rooms with message persistence in a MySQL database.
- API versioning to ensure backward compatibility for future updates.

---

## 🛠️ **Tech Stack**

**Backend:**
- **Spring Boot:** REST API, JWT Authentication, Spring Security
- **WebSockets:** Real-time communication
- **MySQL:** Database for storing user, room, and message data
- **Spring Data JPA:** For interacting with MySQL

**Frontend:**
- **React:** Modern UI with hooks for state management
- **Vite:** Fast build tool and dev server
- **Axios:** For making API calls
- **WebSocket API:** To handle real-time message updates

---

## ✨ **Features**

- **Authentication**: JWT-based authentication system to secure endpoints.
- **Real-Time Messaging**: WebSocket integration for live message updates between users.
- **Room Management**: Users can create rooms, join rooms, and send messages.
- **Message History**: Messages are persisted in a MySQL database for retrieval.
- **API Versioning**: Easily extendable API with versioning (e.g., `/api/v1`, `/api/v2`).
  
---

## 📑 **API Documentation**

### **Authentication API**

#### **POST** `/api/v1/auth/register`
- **Description**: Register a new user.
- **Request Body**:

  ```json
  
  {
    "username": "example",
    "password": "securepassword"
  }

```

Response: 201 Created
POST /api/v1/auth/login
Description: Login and retrieve JWT token.
Request Body:

```json

{
  "username": "example",
  "password": "securepassword"
}

```

Response:

```json

{
  "token": "JWT_TOKEN_HERE"
}
```

Room API
#### **GET** `/api/v1/rooms`
Description: Get a list of all rooms.
Response: 200 OK

```json

[
  {
    "id": 1,
    "name": "General Chat",
    "creatorUsername": "admin",
    "messages": []
  }
]

```

#### **POST** `/api/v1/rooms`
Description: Create a new chat room.
Request Body:

```json

{
  "name": "New Room"
}

```
Response: 201 Created

#### **POST** `/api/v1/rooms/{roomId}/join`
Description: Join a specific room.
Response: 200 OK

Message API
#### **GET** `/api/v1/messages/room/{roomId}`
Description: Get all messages in a room.
Response: 200 OK

```json

[
  {
    "id": 1,
    "content": "Hello, world!",
    "senderUsername": "user1",
    "timestamp": "2025-02-01T09:00:00"
  }
]

```

#### **POST** `/api/v1/messages/send`
Description: Send a message to a room.
Request Body:

```json

{
  "content": "Hello from the new room!",
  "roomId": 1
}

```

Response: 200 OK

## 🛠️ **Setup Instructions**

## **Frontend Setup**
**Clone the repository:**

```bash

git clone https://github.com/yourusername/LiveChat.git
cd LiveChat/frontend

```

**Install dependencies:**

```bash

npm install
Run the development server:

```

**Start React project:**


```bash

npm run dev

```

Visit the app at `http://localhost:5173`.

## Backend Setup**
### **Clone the repository:**

```bash

git clone https://github.com/yourusername/LiveChat.git
cd LiveChat/backend

```

**Install dependencies:**

```bash

./gradlew build

```

**Run the Spring Boot application:**

```bash

./gradlew bootRun

```

Visit the app at `http://localhost:8096`.

## **Database Setup**

Install MySQL (or use a cloud database).

**Create a database:**

```sql

CREATE DATABASE livechat;

```

Set the database credentials in application.properties:

```properties

spring.datasource.url=jdbc:mysql://localhost:3306/livechat
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

```

Run the application to allow Spring Boot to initialize the database.

## 🧪 **Testing**
### **Unit Tests**

Run the unit tests for the backend:

```bash

./gradlew test

```

### **Frontend Tests**
Run the frontend tests:

```bash

npm test

```

## 🤝 **Contributions**
Contributions are welcome! Please feel free to fork the repository, submit issues, and make pull requests. Here's how to contribute:

**Fork the repository**.
Create a new branch (git checkout -b feature-name).
Make your changes.
Commit your changes (git commit -am 'Add new feature').
Push to the branch (git push origin feature-name).
Create a pull request.

## 📄 **License**
This project is licensed under the MIT License - see the LICENSE file for details.

Thank you for checking out LiveChat!
For any questions or feedback, feel free to reach out!

Optional Enhancements:
Improved error handling with meaningful error messages.
Rate limiting for message posting to prevent spam.
Message storage in a file or cloud if scaling is required.
