
# Learning Management System (LMS) - Setup Guide

## Project Overview

A lightweight Java-based Learning Management System featuring a graphical user interface for managing students, courses, and enrollments. The application uses SQLite for persistent data storage and Swing for the GUI layer.

## Project Structure

```
StudyBuddy_2/
│
├── src/                          # Source code directory
│   ├── LMSApp.java              # Main application class (GUI and user interface)
│   ├── Database.java            # Database operations and SQL queries
│   ├── LMSApp.class             # Compiled bytecode
│   └── Database.class            # Compiled bytecode
│
├── lib/                          # External libraries directory
│   └── sqlite-jdbc-3.42.0.0.jar # SQLite JDBC driver
│
├── out/                          # Compiled output directory
│   ├── LMSApp.class             # Compiled application class
│   └── Database.class            # Compiled database class
│
├── lms.db                        # SQLite database file (created automatically)
│
└── README.md                     # Project documentation
```

## Code Hierarchy

### Class Structure

#### 1. **LMSApp** (Main Application Class)
   - **Purpose**: Handles all GUI components and user interactions
   - **Responsibilities**:
     - Creates and manages Swing GUI components (JFrame, JPanel, JButton, JTextField, etc.)
     - Handles user input and button click events
     - Displays data in the output area
     - Shows success/error messages via JOptionPane dialogs
     - Coordinates between UI and database operations
   
   - **Key Methods**:
     - `main(String[] args)` - Application entry point
     - `createAndShowGUI()` - Initializes and displays the GUI
     - `refreshOutput()` - Updates the display area with current data

#### 2. **Database** (Data Access Layer)
   - **Purpose**: Manages all database operations and SQL queries
   - **Responsibilities**:
     - Establishes database connections
     - Creates database tables on initialization
     - Performs CRUD operations (Create, Read, Delete)
     - Handles SQL exceptions
   
   - **Key Methods**:
     - `getConnection()` - Returns database connection
     - `initDatabase()` - Creates tables if they don't exist
     - `addStudent(String name)` - Adds a new student
     - `addCourse(String name)` - Adds a new course
     - `enrollStudent(int studentId, int courseId)` - Creates enrollment
     - `deleteStudent(int studentId)` - Deletes student and related enrollments
     - `deleteCourse(int courseId)` - Deletes course and related enrollments
     - `deleteEnrollment(int enrollmentId)` - Deletes specific enrollment
     - `listStudents()` - Retrieves all students
     - `listCourses()` - Retrieves all courses
     - `listEnrollments()` - Retrieves all enrollments with joins

### Architecture Flow

```
User Input (GUI)
    ↓
LMSApp (Action Listeners)
    ↓
Database (SQL Operations)
    ↓
SQLite Database (lms.db)
    ↓
Database (Result Processing)
    ↓
LMSApp (Display Update)
    ↓
User View (GUI Output)
```

### Component Dependencies

- **LMSApp** depends on:
  - `Database` class for all data operations
  - `javax.swing.*` for GUI components
  - `java.util.List` for data structures

- **Database** depends on:
  - `java.sql.*` for JDBC operations
  - `java.util.List` and `java.util.ArrayList` for data handling
  - SQLite JDBC driver (from `lib/` directory)

## Core Features

- **Student Management**: Add, view, and delete students
- **Course Management**: Add, view, and delete courses  
- **Enrollment System**: Enroll students in courses, track enrollments, and delete enrollments
- **Real-time Display**: View all data with refresh functionality
- **Error Handling**: Comprehensive try-catch blocks with user-friendly error messages and success notifications
- **Data Integrity**: Automatic cleanup of related enrollments when deleting students or courses

## System Requirements

- Java 8 or higher
- SQLite JDBC Driver (bundled or downloadable)

## Dependencies

### GUI Framework
- **Javax.Swing**: Built-in Java GUI toolkit (no external dependency)

### Database
- **SQLite**: Lightweight embedded database
- **JDBC Driver**: `sqlite-jdbc-*.jar` (add to project classpath)

## Installation Steps

1. Download and add `sqlite-jdbc` JAR to your project's `lib/` folder
2. Update your IDE's classpath to include the JDBC driver
3. Place `Database.java` and `LMSApp.java` in `src/` directory
4. Compile and run `LMSApp.java`

## Usage

1. Launch the application
2. **Add Operations**: Add students and courses using input fields
3. **Enrollment**: Enroll students by entering their Student ID and Course ID
4. **Delete Operations**: 
   - Delete students by entering their ID (this will also delete all their enrollments)
   - Delete courses by entering their ID (this will also delete all enrollments for that course)
   - Delete specific enrollments by entering the Enrollment ID
   - All delete operations require confirmation before execution
5. **View Data**: Click "Refresh Lists" to view all data, or data refreshes automatically after add/delete operations

## Error Handling

The application includes comprehensive error handling throughout:

- **Input Validation**: Validates user input and displays appropriate error messages for invalid data
- **Database Errors**: Catches and displays SQL exceptions with user-friendly messages
- **Success Notifications**: Shows confirmation messages when operations complete successfully
- **Confirmation Dialogs**: Delete operations require user confirmation to prevent accidental deletions
- **Exception Handling**: All database operations are wrapped in try-catch blocks to ensure application stability

## Database Schema

- **students**: ID, Name
- **courses**: ID, Name  
- **enrollments**: ID, Student ID, Course ID (with foreign keys)
