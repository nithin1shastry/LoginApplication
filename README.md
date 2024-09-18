# Role-Based Authentication System

This project demonstrates a **Spring Boot** application that provides role-based authentication with **Google OAuth2 login**. It features user registration, role-based access control (admin/user), and customized dashboards for different roles.

## Features

- **Google OAuth2 Login**: Users can log in with their Google accounts. The app fetches user details such as profile picture, name, and email.
- **Role-Based Access Control**: Only admin users can access the admin dashboard, while regular users are restricted to the user dashboard.
- **User Registration**: Users can register by creating an account, selecting their role (admin/user).
- **Access Control**: Application restricts access to admin endpoints for non-admin users.

---

## Screenshots

### Login Page
Users can log in with either their credentials or Google authentication.

![Login Page](https://github.com/user-attachments/assets/06e11f32-9eab-44a2-991c-a53a45f86af5)

---

### Registration Page
New users can register by providing their details and selecting a role.

![Registration Page](https://github.com/user-attachments/assets/33346b7d-e9b3-4333-8a6f-ca8c3697acf7)

---

### Admin Dashboard
Accessible only to admin users. The application restricts this page from regular users and throws an error if unauthorized access is attempted.

![Admin Dashboard](https://github.com/user-attachments/assets/5767d814-348a-463f-b8b5-fcf311049e3a)

---

### User Dashboard
Regular users are redirected to the user dashboard, which presents relevant information and restricted access to admin functionalities.

![User Dashboard](https://github.com/user-attachments/assets/7fe96a47-1925-48a0-821b-7f76e4e736f8)

---

### Google OAuth2 Login
Users can log in via their Google accounts. The application fetches and displays their profile picture, name, and email.

![Google Login](https://github.com/user-attachments/assets/a28b3b79-03f9-46fb-bc36-ae9afe2cdb3a)

---

### Database Relations

The application manages users and their roles using a relational database model with three tables: `users`, `roles`, and `users_role` (join table).

- **User Table**: Stores user information such as name, email, and password.
- **Role Table**: Defines different roles in the application (e.g., "USER", "ADMIN").
- **User_Role Table**: Maps the many-to-many relationship between users and roles.

#### Entity Relationships:

- Each user can have multiple roles (e.g., both "USER" and "ADMIN").
- Each role can be assigned to multiple users.
- The `users_role` table acts as the bridge table connecting `users` and `roles` through foreign keys.

#### Schema Overview:

- **User**: Stores user details.
- **Role**: Defines roles within the system.
- **User_Role**: Maps which user has which roles.
  
## Prerequisites

- **Java 11** or later
- **Maven** or **Gradle**
- **MySQL Database** (or any relational database)
- Google OAuth2 credentials (client ID and client secret)


