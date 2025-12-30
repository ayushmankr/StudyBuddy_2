
# Learning Management System (LMS) - Setup Guide

## Project Overview

A lightweight Java-based Learning Management System featuring a graphical user interface for managing students, courses, and enrollments. The application uses SQLite for persistent data storage and Swing for the GUI layer.

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
