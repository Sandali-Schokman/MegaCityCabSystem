package dao;

import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO for managing commission percentage set by Admin
 * Uses Singleton Pattern to ensure only one database connection
 */
public class CommissionDAO {
    private  Connection connection;

    public CommissionDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton Pattern
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the current commission percentage
    public double getCommissionPercentage() {
        String query = "SELECT commission_percentage FROM commission_settings LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("commission_percentage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10.0; // Default 10% commission if not set
    }

    // Update the commission percentage (Admin updates)
    public boolean updateCommissionPercentage(double newCommission) {
        String query = "UPDATE commission_settings SET commission_percentage = ? WHERE id = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, newCommission);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
