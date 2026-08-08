import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {

    public static String[] login(Scanner sc) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("     EMPLOYEE ATTENDANCE SYSTEM");
        System.out.println("              LOGIN");
        System.out.println("======================================");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "SELECT user_id, username, role " +
                     "FROM users " +
                     "WHERE username = ? AND password = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");
                String role = rs.getString("role");

                System.out.println();
                System.out.println("Login Successful!");
                System.out.println("Welcome, " + username);
                System.out.println("Role: " + role);

                return new String[] {
                    String.valueOf(userId),
                    username,
                    role
                };

            } else {

                System.out.println();
                System.out.println("Invalid Username or Password!");
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("Login Error: " + e.getMessage());
        }

        return null;
    }
}