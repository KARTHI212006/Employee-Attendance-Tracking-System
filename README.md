# Employee Attendance Tracking System

A Java Swing & MySQL based application for managing employee attendance, leaves, employee profiles, and generating attendance reports.

## Features

- **User Authentication**: Secure Login for Administrators / HR.
- **Employee Management**: Add, update, view, and manage employee details.
- **Attendance Tracking**: Mark and track daily employee attendance (Present, Absent, Leave).
- **Leave Management**: Apply for leaves, view leave requests, and update leave status.
- **Reports**: Generate attendance and leave reports.
- **Database Connectivity**: Integrated with MySQL database using JDBC driver.

## Tech Stack

- **Language**: Java (JDK 8+)
- **UI Framework**: Java Swing / AWT
- **Database**: MySQL
- **Connector**: MySQL Connector/J (`lib/mysql-connector-j-9.4.0.jar`)

## Project Structure

```text
EmployeeAttendanceSystem/
├── src/
│   ├── Main.java
│   ├── Login.java
│   ├── EmployeeManagement.java
│   ├── Emloyee.java
│   ├── Attendance.java
│   ├── LeaveManagement.java
│   ├── Reports.java
│   ├── DBConnection.java
│   └── TestConnection.java
├── lib/
│   └── mysql-connector-j-9.4.0.jar
└── README.md
```

## Setup & Running

1. **Prerequisites**: Ensure Java JDK and MySQL Server are installed.
2. **Database Setup**: Configure your database connection in `DBConnection.java`.
3. **Compile & Run**:
   Compile the Java files including the MySQL connector JAR in the classpath, then run `Main`.
