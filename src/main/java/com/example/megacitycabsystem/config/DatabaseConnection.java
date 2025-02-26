package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Database credentials
    private final String URL = "jdbc:mysql://localhost:3306/mega_city_cab";
    private final String USER = "root";  // Change if using a different user
    private final String PASSWORD = "";  // Default WAMP password is empty

    // Private constructor for Singleton
    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database Connection Failed!", e);
        }
    }

    // Get single instance of DatabaseConnection
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Get Connection Object
    public Connection getConnection() {
        return connection;
    }

    // Close Connection
    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.connection.close();
                instance = null; // Reset instance
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

