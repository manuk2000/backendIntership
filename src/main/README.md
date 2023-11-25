# Controlless API

This repository contains the source code for the Controlless API. Controlless is an application that manages courses, assignments, grades, user profiles, and provides authentication services.

## Table of Contents

- [Procedure for Registration](#procedure-for-registration)
- [Course Controller](#course-controller)
- [Grade Controller](#grade-controller)
- [User Profile Controller](#user-profile-controller)
- [Authentication Controller](#authentication-controller)
- [Assignment Controller](#assignment-controller)
- [Test Controller](#test-controller)

## Procedure for Registration:

To initiate registration, provide your email. Upon completion, an email will be sent to you containing a token.
Insert this token into the registration JSON along with your email, name, and password.

։
Following this, you can execute 'muq.' As a result, you will obtain an access and refresh token.
By including these tokens in the authorization header of your requests, you will be able to authenticate with the server and proceed with making requests.

## Course Controller

### 1. Get All Courses
- **Endpoint:** `GET /api/courses/v1`
- **Description:** Returns a list of all courses with the ability to filter by status and teacher ID.

### 2. Get Single Course
- **Endpoint:** `GET /api/courses/v1/{id}`
- **Description:** Returns information about a specific course based on its identifier.

### 3. Create Course
- **Endpoint:** `POST /api/courses/v1`
- **Description:** Creates a new course.

### 4. Update Course
- **Endpoint:** `PUT /api/courses/v1/{id}`
- **Description:** Updates information about a specific course based on its identifier.

### 5. Delete Course
- **Endpoint:** `DELETE /api/courses/v1/{id}`
- **Description:** Deletes a specific course based on its identifier.

## Grade Controller

### 1. Submit Grade
- **Endpoint:** `POST /api/assignments/v1/{assignmentId}/grades`
- **Description:** Submits a grade for a specific assignment and student.

### 2. Get Grades for Student
- **Endpoint:** `GET /api/assignments/v1/{assignmentId}/grades/students/{studentId}`
- **Description:** Retrieves grades for a specific student.

### 3. Get Grades for Student in Course
- **Endpoint:** `GET /api/assignments/v1/{assignmentId}/grades/students/{studentId}/courses/{courseId}`
- **Description:** Retrieves grades for a specific student in a specific course.

### 4. Get All Grades
- **Endpoint:** `GET /api/assignments/v1/{assignmentId}/grades/all`
- **Description:** Retrieves all grades.

## User Profile Controller

### 1. Get User Profile
- **Endpoint:** `GET /api/users/v1/{userId}`
- **Description:** Retrieves the user profile based on the user ID.

### 2. Update User Profile
- **Endpoint:** `PUT /api/users/v1/{userId}`
- **Description:** Updates the user profile based on the user ID.

### 3. Create User
- **Endpoint:** `POST /api/users/v1`
- **Description:** Creates a new user profile.

### 4. Delete User
- **Endpoint:** `DELETE /api/users/v1/{userId}`
- **Description:** Deletes the user profile based on the user ID.

## Authentication Controller

### 1. Sign In
- **Endpoint:** `POST /auth/v1/signin`
- **Description:** Authenticates a user based on email and password.

### 2. Register Email
- **Endpoint:** `POST /auth/v1/registerEmail/{email}`
- **Description:** Sends an activation link to the specified email.

### 3. Registration
- **Endpoint:** `POST /auth/v1/registration`
- **Description:** Registers a new user based on the provided information.

### 4. Reset Password
- **Endpoint:** `POST /auth/v1/resetPassword/{email}`
- **Description:** Sends a link for resetting the password to the specified email.

### 5. Forward Password
- **Endpoint:** `POST /auth/v1/resetPassword`
- **Description:** Forwards the password reset using the provided token.

### 6. Refresh Access Token
- **Endpoint:** `GET /auth/v1/token/refresh/{refreshToken}`
- **Description:** Refreshes the access token using a refresh token.

## Assignment Controller

### 1. Get Assignments for Course
- **Endpoint:** `GET /api/courses/v1/{courseId}/assignments`
- **Description:** Retrieves a list of assignments for the specified course.

### 2. Create Assignment
- **Endpoint:** `POST /api/courses/v1/{courseId}/assignments`
- **Description:** Creates a new assignment for the specified course.

### 3. Update Assignment
- **Endpoint:** `PUT /api/courses/v1/{courseId}/assignments/{id}`
- **Description:** Updates the details of the specified assignment.

### 4. Delete Assignment
- **Endpoint:** `DELETE /api/courses/v1/{courseId}/assignments/{id}`
- **Description:** Deletes the specified assignment.

## Test Controller

### 1. Hello Endpoint
- **Endpoint:** `GET /test/hello`
- **Description:** Returns a simple "Hello" message.

