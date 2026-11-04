# Employee Attendance Tracking System

A desktop application built with **Java Swing** and **MySQL** to support employee records, daily attendance, leave management, and reporting.

## Project Overview

The system provides an administrator/HR-oriented interface for managing employee information and tracking attendance statuses such as Present, Absent, and Leave. It connects to MySQL through JDBC.

## Features

- **Login:** Administrator / HR login screen.
- **Employee Management:** Add, update, view, and manage employee details.
- **Attendance Tracking:** Record daily attendance.
- **Leave Management:** Manage leave requests and their status.
- **Reports:** Generate attendance and leave reports.
- **Database Connectivity:** MySQL integration using JDBC.

## Technology Stack

| Component | Technology |
| --- | --- |
| Language | Java (JDK 8+) |
| Desktop UI | Java Swing / AWT |
| Database | MySQL |
| Database Connectivity | JDBC |
| Connector | MySQL Connector/J 9.4.0 |

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

## Getting Started

### Prerequisites

- Java Development Kit (JDK 8 or later)
- MySQL Server
- MySQL Connector/J (included in the `lib/` directory)

### Configuration

1. Clone or download this repository.
2. Create and configure the MySQL database for the application.
3. Open `src/DBConnection.java`.
4. Update the database URL, database name, username, and password for your local MySQL setup.
5. Ensure the MySQL Connector/J JAR is included in the project's classpath.

### Run

Open the project in an IDE such as IntelliJ IDEA, Eclipse, or VS Code with Java support. Configure the MySQL connector in the classpath, then run `Main.java`.

> **Security note:** Use your own local database credentials. Do not commit passwords or other secrets to GitHub.

## Author

**Karthikeyan S**  
B.E. Computer Science & Engineering · Expected 2027

- GitHub: https://github.com/KARTHI212006
- LinkedIn: https://www.linkedin.com/in/karthikeyan-s-467313382/

---

*Built as a practical Java desktop application project.*
