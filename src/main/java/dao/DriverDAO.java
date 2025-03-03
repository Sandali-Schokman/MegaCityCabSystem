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
        String query = "UPDATE drivers SET availability = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, driverId);

            int rowsAffected = stmt.executeUpdate();

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Register new driver with car details
    public boolean registerDriver(int userId, String carModel, String licensePlate) {
        String carQuery = "INSERT INTO cars (model, license_plate) VALUES (?, ?)";
        String driverQuery = "INSERT INTO drivers (user_id, car_id) VALUES (?, LAST_INSERT_ID())";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement carStmt = conn.prepareStatement(carQuery);
             PreparedStatement driverStmt = conn.prepareStatement(driverQuery)) {

            // Insert Car
            carStmt.setString(1, carModel);
            carStmt.setString(2, licensePlate);
            carStmt.executeUpdate();

            // Insert Driver
            driverStmt.setInt(1, userId);
            return driverStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Get driver details by user ID
    public DriverDTO getDriverByUserId(int userId) {
        String query = "SELECT * FROM drivers WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("driver_id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getString("availability"),
                        rs.getDouble("total_earnings")
                );
                return DriverMapper.toDTO(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Fetch all drivers with their availability (for Admin & Manager tracking)
    public List<DriverDTO> getAllDrivers() {
        List<DriverDTO> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";

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
                drivers.add(DriverMapper.toDTO(driver));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
