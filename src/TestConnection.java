import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Test passed: Connection object created successfully.");
        } else {
            System.out.println("Test failed: Unable to establish connection.");
        }
    }
}
