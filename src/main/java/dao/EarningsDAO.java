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
    private  CommissionDAO commissionDAO;

    public EarningsDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton Pattern
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.commissionDAO = new CommissionDAO();
    }

    // Retrieve Driver Earnings
    public EarningsDTO getDriverEarnings(int driverId) {
        double commissionRate = commissionDAO.getCommissionPercentage() / 100; // Convert to decimal
        String query = "SELECT total_earnings, completed_rides FROM drivers WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double totalEarnings = rs.getDouble("total_earnings");
                int completedRides = rs.getInt("completed_rides");

                // Calculate earnings breakdown
                double companyShare = totalEarnings * commissionRate;
                double driverEarnings = totalEarnings - companyShare;

                return new EarningsDTO(driverId, totalEarnings, driverEarnings,companyShare, completedRides);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update earnings after ride payment
    public boolean updateEarnings(int driverId, double fare) {
        double commissionRate = commissionDAO.getCommissionPercentage() / 100;
        double companyShare = fare * commissionRate;
        double driverEarnings = fare - companyShare;

        String query = "UPDATE drivers SET total_earnings = total_earnings + ?, completed_rides = completed_rides + 1 WHERE driver_id = ?";
        String paymentQuery = "INSERT INTO payments (booking_id, amount, driver_earnings, company_share, payment_status) VALUES (?, ?, ?, ?, 'CONFIRMED')";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             PreparedStatement paymentStmt = connection.prepareStatement(paymentQuery)) {

            // Update driver earnings
            stmt.setDouble(1, fare);
            stmt.setInt(2, driverId);
            stmt.executeUpdate();

            // Store payment breakdown
            paymentStmt.setInt(1, driverId);
            paymentStmt.setDouble(2, fare);
            paymentStmt.setDouble(3, driverEarnings);
            paymentStmt.setDouble(4, companyShare);
            paymentStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Get earnings for all drivers (for admin & manager)
    public List<EarningsDTO> getAllDriversEarnings() {
        List<EarningsDTO> earningsList = new ArrayList<>();
        String query = "SELECT * FROM drivers";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                earningsList.add(new EarningsDTO(
                        rs.getInt("driver_id"),
                        rs.getDouble("total_earnings"),
                        0,
                        0,
                        rs.getInt("completed_rides")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earningsList;
    }
}
