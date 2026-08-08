import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Reports {

    public static void menu(Scanner sc) {

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("              REPORTS");
            System.out.println("======================================");

            System.out.println("1. Employee Attendance Report");
            System.out.println("2. Leave Report");
            System.out.println("3. Overall Attendance Summary");
            System.out.println("4. Back");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    employeeAttendanceReport(sc);
                    break;

                case "2":
                    leaveReport();
                    break;

                case "3":
                    overallAttendance();
                    break;

                case "4":
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void employeeAttendanceReport(
            Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");

            int employeeId =
                    Integer.parseInt(sc.nextLine());

            String sql =
                    "SELECT " +
                    "e.employee_name, " +

                    "SUM(CASE WHEN a.status = 'PRESENT' " +
                    "THEN 1 ELSE 0 END) AS present_count, " +

                    "SUM(CASE WHEN a.status = 'ABSENT' " +
                    "THEN 1 ELSE 0 END) AS absent_count, " +

                    "SUM(CASE WHEN a.status = 'HALF_DAY' " +
                    "THEN 1 ELSE 0 END) AS halfday_count " +

                    "FROM employees e " +

                    "LEFT JOIN attendance a " +
                    "ON e.employee_id = a.employee_id " +

                    "WHERE e.employee_id = ? " +

                    "GROUP BY e.employee_id, e.employee_name";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    String name =
                            rs.getString("employee_name");

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
                            "     EMPLOYEE ATTENDANCE REPORT"
                    );

                    System.out.println(
                            "======================================"
                    );

                    System.out.println(
                            "Employee ID   : " + employeeId
                    );

                    System.out.println(
                            "Employee Name : " + name
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
                            "Total          : " + total
                    );

                    System.out.printf(
                            "Attendance %%  : %.2f%%\n",
                            percentage
                    );

                    System.out.println(
                            "======================================"
                    );

                } else {

                    System.out.println(
                            "Employee not found!"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Report Error: " + e.getMessage()
            );
        }
    }

    private static void leaveReport() {

        String sql =
                "SELECT " +
                "COUNT(*) AS total_leaves, " +

                "SUM(CASE WHEN status = 'PENDING' " +
                "THEN 1 ELSE 0 END) AS pending, " +

                "SUM(CASE WHEN status = 'APPROVED' " +
                "THEN 1 ELSE 0 END) AS approved, " +

                "SUM(CASE WHEN status = 'REJECTED' " +
                "THEN 1 ELSE 0 END) AS rejected " +

                "FROM leaves";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                System.out.println();
                System.out.println(
                        "========== LEAVE REPORT =========="
                );

                System.out.println(
                        "Total Leaves : "
                        + rs.getInt("total_leaves")
                );

                System.out.println(
                        "Pending      : "
                        + rs.getInt("pending")
                );

                System.out.println(
                        "Approved     : "
                        + rs.getInt("approved")
                );

                System.out.println(
                        "Rejected     : "
                        + rs.getInt("rejected")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Report Error: " + e.getMessage()
            );
        }
    }

    private static void overallAttendance() {

        String sql =
                "SELECT " +

                "COUNT(*) AS total, " +

                "SUM(CASE WHEN status = 'PRESENT' " +
                "THEN 1 ELSE 0 END) AS present, " +

                "SUM(CASE WHEN status = 'ABSENT' " +
                "THEN 1 ELSE 0 END) AS absent, " +

                "SUM(CASE WHEN status = 'HALF_DAY' " +
                "THEN 1 ELSE 0 END) AS halfday " +

                "FROM attendance";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                int total =
                        rs.getInt("total");

                int present =
                        rs.getInt("present");

                int absent =
                        rs.getInt("absent");

                int halfDay =
                        rs.getInt("halfday");

                double percentage = 0;

                if (total > 0) {

                    percentage =
                            ((present +
                            (halfDay * 0.5))
                            / total) * 100;
                }

                System.out.println();
                System.out.println(
                        "======= OVERALL ATTENDANCE ======="
                );

                System.out.println(
                        "Total Records : " + total
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

                System.out.printf(
                        "Attendance %%  : %.2f%%\n",
                        percentage
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Report Error: " + e.getMessage()
            );
        }
    }
}