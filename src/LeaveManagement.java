import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LeaveManagement {

    public static void menu(Scanner sc) {

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("          LEAVE MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Apply Leave");
            System.out.println("2. View All Leaves");
            System.out.println("3. Search Employee Leave");
            System.out.println("4. Approve Leave");
            System.out.println("5. Reject Leave");
            System.out.println("6. Back");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    applyLeave(sc);
                    break;

                case "2":
                    viewLeaves();
                    break;

                case "3":
                    searchLeave(sc);
                    break;

                case "4":
                    updateLeaveStatus(sc, "APPROVED");
                    break;

                case "5":
                    updateLeaveStatus(sc, "REJECTED");
                    break;

                case "6":
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void applyLeave(Scanner sc) {

        try {

            System.out.println();
            System.out.println("---------- APPLY LEAVE ----------");

            System.out.print("Employee ID: ");
            int employeeId =
                    Integer.parseInt(sc.nextLine());

            System.out.print("Leave Type: ");
            String leaveType = sc.nextLine();

            System.out.print("From Date (YYYY-MM-DD): ");
            String fromDate = sc.nextLine();

            System.out.print("To Date (YYYY-MM-DD): ");
            String toDate = sc.nextLine();

            System.out.print("Reason: ");
            String reason = sc.nextLine();

            String sql =
                    "INSERT INTO leaves " +
                    "(employee_id, leave_type, from_date, " +
                    "to_date, reason, status) " +
                    "VALUES (?, ?, ?, ?, ?, 'PENDING')";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);
                ps.setString(2, leaveType);
                ps.setDate(
                        3,
                        java.sql.Date.valueOf(fromDate)
                );
                ps.setDate(
                        4,
                        java.sql.Date.valueOf(toDate)
                );
                ps.setString(5, reason);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println(
                            "Leave Applied Successfully!"
                    );

                    System.out.println(
                            "Status: PENDING"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Leave Error: " + e.getMessage()
            );
        }
    }

    private static void viewLeaves() {

        String sql =
                "SELECT l.*, e.employee_name " +
                "FROM leaves l " +
                "JOIN employees e " +
                "ON l.employee_id = e.employee_id " +
                "ORDER BY l.applied_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println(
                    "============ ALL LEAVES ============"
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "--------------------------------------"
                );

                System.out.println(
                        "Leave ID      : "
                        + rs.getInt("leave_id")
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
                        "Leave Type    : "
                        + rs.getString("leave_type")
                );

                System.out.println(
                        "From Date     : "
                        + rs.getDate("from_date")
                );

                System.out.println(
                        "To Date       : "
                        + rs.getDate("to_date")
                );

                System.out.println(
                        "Reason        : "
                        + rs.getString("reason")
                );

                System.out.println(
                        "Status        : "
                        + rs.getString("status")
                );
            }

            if (!found) {

                System.out.println(
                        "No leave records found."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void searchLeave(Scanner sc) {

        try {

            System.out.print("Employee ID: ");

            int employeeId =
                    Integer.parseInt(sc.nextLine());

            String sql =
                    "SELECT * FROM leaves " +
                    "WHERE employee_id = ? " +
                    "ORDER BY from_date DESC";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);

                ResultSet rs = ps.executeQuery();

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println();
                    System.out.println(
                            "Leave ID   : "
                            + rs.getInt("leave_id")
                    );

                    System.out.println(
                            "Leave Type : "
                            + rs.getString("leave_type")
                    );

                    System.out.println(
                            "From       : "
                            + rs.getDate("from_date")
                    );

                    System.out.println(
                            "To         : "
                            + rs.getDate("to_date")
                    );

                    System.out.println(
                            "Reason     : "
                            + rs.getString("reason")
                    );

                    System.out.println(
                            "Status     : "
                            + rs.getString("status")
                    );

                    System.out.println("--------------------------------");
                }

                if (!found) {

                    System.out.println(
                            "No leave records found!"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search Error: " + e.getMessage()
            );
        }
    }

    private static void updateLeaveStatus(
            Scanner sc,
            String status) {

        try {

            System.out.print("Enter Leave ID: ");

            int leaveId =
                    Integer.parseInt(sc.nextLine());

            String sql =
                    "UPDATE leaves " +
                    "SET status = ? " +
                    "WHERE leave_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setString(1, status);
                ps.setInt(2, leaveId);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println(
                            "Leave status updated to "
                            + status
                    );

                } else {

                    System.out.println(
                            "Leave ID not found!"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Update Error: " + e.getMessage()
            );
        }
    }
}