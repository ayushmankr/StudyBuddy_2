
# Learning Management System (LMS) - Setup Guide

## Project Overview

A lightweight Java-based Learning Management System featuring a graphical user interface for managing students, courses, and enrollments. The application uses SQLite for persistent data storage and Swing for the GUI layer.

## Core Features

- **Student Management**: Add and view students
- **Course Management**: Add and view courses  
- **Enrollment System**: Enroll students in courses and track enrollments
- **Real-time Display**: View all data with refresh functionality

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
2. Add students and courses using input fields
3. Enroll students by entering their ID and course ID
4. Click "Refresh Lists" to view all data

## Database Schema

- **students**: ID, Name
- **courses**: ID, Name  
- **enrollments**: ID, Student ID, Course ID (with foreign keys)
