package dao;

import config.DatabaseConnection;
import dto.DriverDTO;
import mappers.DriverMapper;
import models.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    public List<DriverDTO> getAvailableDrivers() {
        List<DriverDTO> availableDrivers = new ArrayList<>();
        String query = "SELECT * FROM drivers WHERE availability = 'AVAILABLE'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("driver_id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getString("availability"),
                        rs.getDouble("total_earnings")
                );
                availableDrivers.add(DriverMapper.toDTO(driver));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableDrivers;
    }

    public boolean updateDriverAvailability(int driverId, String status) {
        String query = "UPDATE drivers SET availability = ? WHERE driver_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, driverId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
