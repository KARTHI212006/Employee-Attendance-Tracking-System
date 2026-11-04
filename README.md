# Employee Attendance Tracking System

A console-based Java application that uses **JDBC** and **MySQL** to manage employee records, daily attendance, leave requests, and attendance reports, with separate **Admin** and **Employee** roles.

## Overview

The application starts at a login screen. After authentication, the menu depends on the user's role stored in the database:

- **ADMIN** can manage employees, mark attendance, handle leave requests, and view reports.
- **EMPLOYEE** can view their own attendance, apply for leave, and check their leave status.

It is a learning project focused on Java OOP structure, JDBC data access, and relational database design.

## Features

**Admin dashboard**
- Employee management: add, view, and update employees.
- Attendance: record daily attendance as `PRESENT` or `ABSENT`.
- Leave management: review leave requests and update their status.
- Reports: attendance summaries (present / absent counts per employee).

**Employee dashboard**
- View personal attendance records.
- Apply for leave.
- View personal leave requests.

## Technology Stack

| Area | Technology |
| --- | --- |
| Language | Java |
| Interface | Console (`Scanner`-based menus) |
| Database | MySQL |
| Data access | JDBC with `PreparedStatement` |
| Driver | MySQL Connector/J 9.4.0 (`lib/mysql-connector-j-9.4.0.jar`) |

## Project Structure

```text
.
├── lib/
│   └── mysql-connector-j-9.4.0.jar
├── src/
│   ├── Main.java              # Entry point, login, admin/employee menus
│   ├── Login.java             # Credential check against the users table
│   ├── DBConnection.java      # JDBC connection settings
│   ├── TestConnection.java    # Quick database connectivity check
│   ├── Emloyee.java           # Employee operations (file name spelled as in the source)
│   ├── EmployeeManagement.java
│   ├── Attendance.java
│   ├── LeaveManagement.java
│   └── Reports.java
└── README.md
```

## Prerequisites

- Java Development Kit (JDK 8 or later)
- MySQL Server running locally
- The bundled MySQL Connector/J JAR in `lib/`

## Database Configuration

The code connects to a local MySQL database named `employee_attendance_system` on port `3306`. A SQL schema script is **not included** in this repository yet. From the queries in the source, the application expects these tables:

| Table | Used for |
| --- | --- |
| `users` | Login (`user_id`, `username`, `role` of `ADMIN` or `EMPLOYEE`) |
| `employees` | Employee records (includes `employee_id`, `employee_name`) |
| `attendance` | Daily attendance (`attendance_id`, employee reference, `status`) |
| `leaves` | Leave requests and their status |

Exact column definitions should be taken from the `INSERT` and `SELECT` statements in `src/`.

1. Create the database: `CREATE DATABASE employee_attendance_system;`
2. Create the tables above and add at least one `ADMIN` user.
3. Open `src/DBConnection.java` and set the MySQL username and password for **your own** local server.

> **Security note:** Database credentials are currently set directly in `DBConnection.java`. Use your own local credentials and avoid committing real passwords. A planned improvement is to read them from environment variables.

## Build and Run

From the repository root:

```bash
# Linux / macOS
mkdir -p out
javac -cp "lib/mysql-connector-j-9.4.0.jar" -d out src/*.java
java -cp "out:lib/mysql-connector-j-9.4.0.jar" Main
```

```bat
:: Windows
mkdir out
javac -cp "lib\mysql-connector-j-9.4.0.jar" -d out src\*.java
java -cp "out;lib\mysql-connector-j-9.4.0.jar" Main
```

To check the database connection alone, run `TestConnection` the same way.

## Current Status

Working console application for the flows listed above. No automated tests, schema script, or screenshots are included yet.

## Future Improvements

- Add a `schema.sql` with table definitions and sample data.
- Move database credentials to environment variables or a config file.
- Hash stored passwords.
- Add input validation and unit tests.

## Author

**Karthikeyan S** — B.E. Computer Science & Engineering (Expected 2027)

- GitHub: [KARTHI212006](https://github.com/KARTHI212006)
- Portfolio: [s-karthikeyan-portfolio.vercel.app](https://s-karthikeyan-portfolio.vercel.app/)
- LinkedIn: [karthikeyan-s](https://www.linkedin.com/in/karthikeyan-s-467313382/)
