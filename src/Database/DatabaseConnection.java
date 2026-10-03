package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Creates MySQL connections. Change the settings below if your XAMPP setup is different. */
public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/billing_system?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /** Converts a technical SQLException into a message a normal user can understand. */
    public static String getFriendlyMessage(SQLException e) {
        int code = e.getErrorCode();
        String state = e.getSQLState();
        String text = e.getMessage() == null ? "" : e.getMessage();

        if (text.contains("No suitable driver")) {
            return "MySQL Connector/J (JAR file) was not found.\nAdd it to the lib folder and project libraries.";
        } else if (code == 1049) {
            return "Database 'billing_system' does not exist.\nImport billing_system.sql using phpMyAdmin.";
        } else if (code == 1045) {
            return "Access denied. The MySQL username or password in DatabaseConnection.java is wrong.";
        } else if (code == 1146) {
            return "A required table is missing.\nRe-import billing_system.sql using phpMyAdmin.";
        } else if (code == 1062) {
            return "This value already exists (duplicate entry).";
        } else if (state != null && state.startsWith("08")) {
            return "Cannot connect to MySQL.\nOpen XAMPP Control Panel and click Start next to MySQL.";
        }
        return "Database error: " + text;
    }
}
