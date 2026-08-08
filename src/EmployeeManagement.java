import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class EmployeeManagement {

    public static void menu(Scanner sc) {

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("        EMPLOYEE MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Back");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    addEmployee(sc);
                    break;

                case "2":
                    viewEmployees();
                    break;

                case "3":
                    searchEmployee(sc);
                    break;

                case "4":
                    updateEmployee(sc);
                    break;

                case "5":
                    deleteEmployee(sc);
                    break;

                case "6":
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ==========================================
    // ADD EMPLOYEE
    // ==========================================

    private static void addEmployee(Scanner sc) {

        try {

            System.out.println();
            System.out.println("---------- ADD EMPLOYEE ----------");

            System.out.print("User ID: ");
            int userId = Integer.parseInt(sc.nextLine());

            System.out.print("Employee Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Gender (Male/Female/Other): ");
            String gender = sc.nextLine();

            System.out.print("Phone: ");
            String phone = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Department: ");
            String department = sc.nextLine();

            System.out.print("Designation: ");
            String designation = sc.nextLine();

            System.out.print("Joining Date (YYYY-MM-DD): ");
            String joiningDate = sc.nextLine();

            String sql = "INSERT INTO employees " +
                    "(user_id, employee_name, age, gender, " +
                    "phone, email, department, designation, joining_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setString(2, name);
                ps.setInt(3, age);
                ps.setString(4, gender);
                ps.setString(5, phone);
                ps.setString(6, email);
                ps.setString(7, department);
                ps.setString(8, designation);
                ps.setDate(
                        9,
                        java.sql.Date.valueOf(joiningDate));

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println();
                    System.out.println(
                            "Employee added successfully!");

                } else {

                    System.out.println(
                            "Employee could not be added.");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Add Employee Error: "
                            + e.getMessage());
        }
    }

    // ==========================================
    // VIEW ALL EMPLOYEES
    // ==========================================

    private static void viewEmployees() {

        String sql = "SELECT * FROM employees " +
                "ORDER BY employee_id";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.println(
                    "========== EMPLOYEE LIST ==========");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println();
                System.out.println(
                        "--------------------------------------");

                System.out.println(
                        "Employee ID : "
                                + rs.getInt("employee_id"));

                System.out.println(
                        "User ID     : "
                                + rs.getInt("user_id"));

                System.out.println(
                        "Name        : "
                                + rs.getString("employee_name"));

                System.out.println(
                        "Age         : "
                                + rs.getInt("age"));

                System.out.println(
                        "Gender      : "
                                + rs.getString("gender"));

                System.out.println(
                        "Phone       : "
                                + rs.getString("phone"));

                System.out.println(
                        "Email       : "
                                + rs.getString("email"));

                System.out.println(
                        "Department  : "
                                + rs.getString("department"));

                System.out.println(
                        "Designation : "
                                + rs.getString("designation"));

                System.out.println(
                        "Joining Date: "
                                + rs.getDate("joining_date"));
            }

            if (!found) {

                System.out.println(
                        "No employees found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "View Employee Error: "
                            + e.getMessage());
        }
    }

    // ==========================================
    // SEARCH EMPLOYEE
    // ==========================================

    private static void searchEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");

            int employeeId = Integer.parseInt(sc.nextLine());

            String sql = "SELECT * FROM employees " +
                    "WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    System.out.println();
                    System.out.println(
                            "========== EMPLOYEE DETAILS ==========");

                    System.out.println(
                            "Employee ID : "
                                    + rs.getInt("employee_id"));

                    System.out.println(
                            "User ID     : "
                                    + rs.getInt("user_id"));

                    System.out.println(
                            "Name        : "
                                    + rs.getString("employee_name"));

                    System.out.println(
                            "Age         : "
                                    + rs.getInt("age"));

                    System.out.println(
                            "Gender      : "
                                    + rs.getString("gender"));

                    System.out.println(
                            "Phone       : "
                                    + rs.getString("phone"));

                    System.out.println(
                            "Email       : "
                                    + rs.getString("email"));

                    System.out.println(
                            "Department  : "
                                    + rs.getString("department"));

                    System.out.println(
                            "Designation : "
                                    + rs.getString("designation"));

                    System.out.println(
                            "Joining Date: "
                                    + rs.getDate("joining_date"));

                } else {

                    System.out.println(
                            "Employee not found!");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search Error: "
                            + e.getMessage());
        }
    }

    // ==========================================
    // UPDATE EMPLOYEE
    // ==========================================

    private static void updateEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");

            int employeeId = Integer.parseInt(sc.nextLine());

            System.out.print("New Employee Name: ");
            String name = sc.nextLine();

            System.out.print("New Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("New Gender: ");
            String gender = sc.nextLine();

            System.out.print("New Phone: ");
            String phone = sc.nextLine();

            System.out.print("New Email: ");
            String email = sc.nextLine();

            System.out.print("New Department: ");
            String department = sc.nextLine();

            System.out.print("New Designation: ");
            String designation = sc.nextLine();

            String sql = "UPDATE employees SET " +
                    "employee_name = ?, " +
                    "age = ?, " +
                    "gender = ?, " +
                    "phone = ?, " +
                    "email = ?, " +
                    "department = ?, " +
                    "designation = ? " +
                    "WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setString(3, gender);
                ps.setString(4, phone);
                ps.setString(5, email);
                ps.setString(6, department);
                ps.setString(7, designation);
                ps.setInt(8, employeeId);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println(
                            "Employee updated successfully!");

                } else {

                    System.out.println(
                            "Employee ID not found!");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Update Employee Error: "
                            + e.getMessage());
        }
    }

    // ==========================================
    // DELETE EMPLOYEE
    // ==========================================

    private static void deleteEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");

            int employeeId = Integer.parseInt(sc.nextLine());

            System.out.print(
                    "Are you sure you want to delete? (yes/no): ");

            String confirm = sc.nextLine();

            if (!confirm.equalsIgnoreCase("yes")) {

                System.out.println(
                        "Delete operation cancelled.");

                return;
            }

            String sql = "DELETE FROM employees " +
                    "WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, employeeId);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println(
                            "Employee deleted successfully!");

                } else {

                    System.out.println(
                            "Employee ID not found!");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Delete Employee Error: "
                            + e.getMessage());
        }
    }
}