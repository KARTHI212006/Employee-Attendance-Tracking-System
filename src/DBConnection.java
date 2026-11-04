import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Creates JDBC connections to the MySQL database.
 *
 * Settings are resolved in this order, per key:
 *   1. Environment variable (DB_URL, DB_USER, DB_PASSWORD)
 *   2. Optional local file "db.properties" in the working directory (not tracked by Git)
 *   3. Local-development defaults: localhost URL, user "root", empty password
 *
 * No password is stored in the source code. See README.md and db.properties.example.
 */
public class DBConnection {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/employee_attendance_system";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static final Properties FILE_SETTINGS = loadFileSettings();

    private static Properties loadFileSettings() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("db.properties")) {
            props.load(in);
        } catch (IOException e) {
            // File is optional; environment variables and defaults are used instead.
        }
        return props;
    }

    private static String setting(String key, String fallback) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            value = FILE_SETTINGS.getProperty(key);
        }
        if (value == null || value.isEmpty()) {
            value = fallback;
        }
        return value;
    }

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    setting("DB_URL", DEFAULT_URL),
                    setting("DB_USER", DEFAULT_USER),
                    setting("DB_PASSWORD", DEFAULT_PASSWORD));

            return con;

        } catch (Exception e) {

            System.out.println(
                    "Database Connection Failed: "
                            + e.getMessage());
            System.out.println(
                    "Check DB_URL, DB_USER and DB_PASSWORD (environment variables or db.properties).");

            return null;
        }
    }
}
