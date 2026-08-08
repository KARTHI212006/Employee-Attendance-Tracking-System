import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Attendance {

    public static void menu(Scanner sc) {

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("         ATTENDANCE MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Mark Attendance");
            System.out.println("2. View All Attendance");
            System.out.println("3. Search Attendance");
            System.out.println("4. Attendance Summary");
            System.out.println("5. Back");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    markAttendance(sc);
                    break;

                case "2":
                    viewAttendance();
                    break;

                case "3":
                    searchAttendance(sc);
                    break;

                case "4":
                    attendanceSummary(sc);
                    break;

                case "5":
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ==========================================
    // MARK ATTENDANCE
    // ==========================================

    private static void markAttendance(Scanner sc) {

        try {

            System.out.println();
            System.out.println("---------- MARK ATTENDANCE ----------");

            System.out.print("Enter Employee ID: ");
            int employeeId = Integer.parseInt(sc.nextLine());

            LocalDate today = LocalDate.now();

            // Check whether employee exists
            String employeeSql =
                    "SELECT employee_name FROM employees WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement employeePs =
                         con.prepareStatement(employeeSql)) {

                employeePs.setInt(1, employeeId);

                ResultSet employeeRs = employeePs.executeQuery();

                if (!employeeRs.next()) {

                    System.out.println("Employee not found!");
                    return;
                }

                String employeeName =
                        employeeRs.getString("employee_name");

                System.out.println("Employee Name: " + employeeName);
            }

            // Check duplicate attendance
            String checkSql =
                    "SELECT attendance_id FROM attendance " +
                    "WHERE employee_id = ? AND attendance_date = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement checkPs =
                         con.prepareStatement(checkSql)) {

                checkPs.setInt(1, employeeId);
                checkPs.setDate(2, Date.valueOf(today));

                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {

                    System.out.println();
                    System.out.println(
                            "Attendance already marked for today!"
                    );

                    return;
                }
            }

            System.out.println();
            System.out.println("Select Status:");
            System.out.println("1. PRESENT");
            System.out.println("2. ABSENT");
            System.out.println("3. HALF_DAY");

            System.out.print("Enter choice: ");

            String statusChoice = sc.nextLine();

            String status;

            switch (statusChoice) {

                case "1":
                    status = "PRESENT";
                    break;

                case "2":
                    status = "ABSENT";
                    break;

                case "3":
                    status = "HALF_DAY";
                    break;

                default:
                    System.out.println("Invalid status!");
                    return;
            }

            LocalTime checkIn = null;
            LocalTime checkOut = null;

            if (status.equals("PRESENT") ||
                status.equals("HALF_DAY")) {

                System.out.print(
                        "Enter Check-In Time (HH:MM): "
                );

                String in = sc.nextLine();

                checkIn = LocalTime.parse(in + ":00");

                System.out.print(
                        "Enter Check-Out Time (HH:MM): "
                );

                String out = sc.nextLine();

                checkOut = LocalTime.parse(out + ":00");
            }

            String insertSql =
                    "INSERT INTO attendance " +
                    "(employee_id, attendance_date, status, " +
                    "check_in, check_out) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(insertSql)) {

                ps.setInt(1, employeeId);
                ps.setDate(2, Date.valueOf(today));
                ps.setString(3, status);

                if (checkIn != null) {
                    ps.setTime(4, Time.valueOf(checkIn));
                } else {
                    ps.setNull(4, java.sql.Types.TIME);
                }

                if (checkOut != null) {
                    ps.setTime(5, Time.valueOf(checkOut));
                } else {
                    ps.setNull(5, java.sql.Types.TIME);
                }

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println();
                    System.out.println(
                            "Attendance marked successfully!"
                    );

                    System.out.println(
                            "Date   : " + today
                    );

                    System.out.println(
                            "Status : " + status
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Attendance Error: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // VIEW ALL ATTENDANCE
    // ==========================================

    private static void viewAttendance() {

        String sql =
                "SELECT a.attendance_id, " +
                "a.employee_id, " +
                "e.employee_name, " +
                "a.attendance_date, " +
                "a.status, " +
                "a.check_in, " +
                "a.check_out " +
                "FROM attendance a " +
                "JOIN employees e " +
                "ON a.employee_id = e.employee_id " +
                "ORDER BY a.attendance_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println(
                    "================ ATTENDANCE RECORDS ================"
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("------------------------------------------");

                System.out.println(
                        "Attendance ID : "
                        + rs.getInt("attendance_id")
                );

                System.out.println(
                        "Employee ID   : "
                        + rs.getInt("employee_id")
                );

                System.out.println(
                        "Employee Name : "
                        + rs.getString("employee_name")
                );

                System.out.println(
                        "Date          : "
                        + rs.getDate("attendance_date")
                );

                System.out.println(
                        "Status        : "
                        + rs.getString("status")
                );

                System.out.println(
                        "Check In      : "
                        + rs.getTime("check_in")
                );

                System.out.println(
                        "Check Out     : "
                        + rs.getTime("check_out")
                );
            }

            if (!found) {
                System.out.println("No attendance records found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // SEARCH ATTENDANCE
    // ==========================================

    private static void searchAttendance(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");

            int employeeId =
                    Integer.parseInt(sc.nextLine());

            String sql =
                    "SELECT a.*, e.employee_name " +
                    "FROM attendance a " +
                    "JOIN employees e " +
                    "ON a.employee_id = e.employee_id " +
                    "WHERE a.employee_id = ? " +
                    "ORDER BY a.attendance_date DESC";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);

                ResultSet rs = ps.executeQuery();

                boolean found = false;

                System.out.println();
                System.out.println(
                        "---------- ATTENDANCE HISTORY ----------"
                );

                while (rs.next()) {

                    found = true;

                    System.out.println(
                            "Date   : "
                            + rs.getDate("attendance_date")
                    );

                    System.out.println(
                            "Status : "
                            + rs.getString("status")
                    );

                    System.out.println(
                            "Check In  : "
                            + rs.getTime("check_in")
                    );

                    System.out.println(
                            "Check Out : "
                            + rs.getTime("check_out")
                    );

                    System.out.println("------------------------------------------");
                }

                if (!found) {
                    System.out.println(
                            "No attendance records found!"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search Error: " + e.getMessage()
            );
        }
    }

    // ==========================================
    // ATTENDANCE SUMMARY
    // ==========================================

    private static void attendanceSummary(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");

            int employeeId =
                    Integer.parseInt(sc.nextLine());

            String employeeSql =
                    "SELECT employee_name FROM employees " +
                    "WHERE employee_id = ?";

            String summarySql =
                    "SELECT " +
                    "SUM(CASE WHEN status = 'PRESENT' " +
                    "THEN 1 ELSE 0 END) AS present_count, " +

                    "SUM(CASE WHEN status = 'ABSENT' " +
                    "THEN 1 ELSE 0 END) AS absent_count, " +

                    "SUM(CASE WHEN status = 'HALF_DAY' " +
                    "THEN 1 ELSE 0 END) AS halfday_count " +

                    "FROM attendance " +
                    "WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection()) {

                String employeeName = null;

                try (PreparedStatement ps =
                             con.prepareStatement(employeeSql)) {

                    ps.setInt(1, employeeId);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        employeeName =
                                rs.getString("employee_name");

                    } else {

                        System.out.println(
                                "Employee not found!"
                        );

                        return;
                    }
                }

                try (PreparedStatement ps =
                             con.prepareStatement(summarySql)) {

                    ps.setInt(1, employeeId);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        int present =
                                rs.getInt("present_count");

                        int absent =
                                rs.getInt("absent_count");

                        int halfDay =
                                rs.getInt("halfday_count");

                        int total =
                                present + absent + halfDay;

                        double percentage = 0;

                        if (total > 0) {

                            percentage =
                                    ((present +
                                    (halfDay * 0.5))
                                    / total) * 100;
                        }

                        System.out.println();
                        System.out.println(
                                "======================================"
                        );
                        System.out.println(
                                "       ATTENDANCE SUMMARY"
                        );
                        System.out.println(
                                "======================================"
                        );

                        System.out.println(
                                "Employee ID   : " + employeeId
                        );

                        System.out.println(
                                "Employee Name : " + employeeName
                        );

                        System.out.println(
                                "Present       : " + present
                        );

                        System.out.println(
                                "Absent        : " + absent
                        );

                        System.out.println(
                                "Half Day      : " + halfDay
                        );

                        System.out.println(
                                "Total Records : " + total
                        );

                        System.out.printf(
                                "Attendance %%  : %.2f%%\n",
                                percentage
                        );
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Summary Error: " + e.getMessage()
            );
        }
    }
}