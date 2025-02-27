package tests;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            // Get database connection instance
            Connection conn = DatabaseConnection.getInstance().getConnection();

            if (conn != null) {
                System.out.println("✅ Database Connection Successful!");
            } else {
                System.out.println("❌ Database Connection Failed!");
            }

            // Close the connection after testing
            DatabaseConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
