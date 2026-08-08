import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("   EMPLOYEE ATTENDANCE TRACKING");
            System.out.println("             SYSTEM");
            System.out.println("======================================");

            System.out.println("1. Login");
            System.out.println("2. Exit");

            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":

                    String[] user = Login.login(sc);

                    if (user != null) {

                        String role = user[2];

                        if (role.equals("ADMIN")) {

                            adminDashboard(sc);

                        } else if (role.equals("EMPLOYEE")) {

                            employeeDashboard(sc);

                        } else {

                            System.out.println("Unknown user role!");
                        }
                    }

                    break;

                case "2":

                    System.out.println();
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;

                default:

                    System.out.println("Invalid choice!");
            }
        }
    }

    // ADMIN DASHBOARD
    private static void adminDashboard(Scanner sc) {

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("          ADMIN DASHBOARD");
            System.out.println("======================================");

            System.out.println("1. Employee Management");
            System.out.println("2. Attendance");
            System.out.println("3. Leave Management");
            System.out.println("4. Reports");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    EmployeeManagement.menu(sc);
                    break;

                case "2":
                    Attendance.menu(sc);
                    break;

                case "3":
                    LeaveManagement.menu(sc);
                    break;

                case "4":
                    Reports.menu(sc);
                    break;

                case "5":
                    System.out.println("Logged out successfully!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // EMPLOYEE DASHBOARD
    private static void employeeDashboard(Scanner sc) {

        while (true) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("        EMPLOYEE DASHBOARD");
            System.out.println("======================================");

            System.out.println("1. My Attendance");
            System.out.println("2. Apply Leave");
            System.out.println("3. My Leave");
            System.out.println("4. Logout");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.println("My Attendance module coming next.");
                    break;

                case "2":
                    System.out.println("Apply Leave module coming next.");
                    break;

                case "3":
                    System.out.println("My Leave module coming next.");
                    break;

                case "4":
                    System.out.println("Logged out successfully!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}