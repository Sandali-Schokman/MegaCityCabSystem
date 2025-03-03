package dao;

import config.DatabaseConnection;
import dto.EarningsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EarningsDAO {
    private Connection connection;

    public EarningsDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton Pattern
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve Driver Earnings
    public EarningsDTO getDriverEarnings(int driverId) {
        String query = "SELECT total_earnings, completed_rides, last_payment_date FROM drivers WHERE driver_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new EarningsDTO(
                        driverId,
                        rs.getDouble("total_earnings"),
                        rs.getInt("completed_rides"),
                        rs.getTimestamp("last_payment_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update Driver Earnings After Payment
    public boolean updateEarnings(int driverId, double amount) {
        String query = "UPDATE drivers SET total_earnings = total_earnings + ?, completed_rides = completed_rides + 1 WHERE driver_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, driverId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get earnings for all drivers (for admin & manager)
    public List<EarningsDTO> getAllDriversEarnings() {
        List<EarningsDTO> earningsList = new ArrayList<>();
        String query = "SELECT driver_id, total_earnings, completed_rides, last_payment_date FROM drivers";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                earningsList.add(new EarningsDTO(
                        rs.getInt("driver_id"),
                        rs.getDouble("total_earnings"),
                        rs.getInt("completed_rides"),
                        rs.getTimestamp("last_payment_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earningsList;
    }
}
