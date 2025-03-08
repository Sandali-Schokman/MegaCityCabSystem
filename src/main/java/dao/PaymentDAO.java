package dao;

import config.DatabaseConnection;
import dto.PaymentDTO;
import mappers.PaymentMapper;
import models.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton Pattern
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a Payment Record
    public boolean createPayment(PaymentDTO payment) {
        String query = "INSERT INTO payments (booking_id, amount, method, payment_status, driver_earnings, company_share) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, payment.getBookingId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getMethod());
            stmt.setString(4, payment.getPaymentStatus());
            stmt.setDouble(5, payment.getDriverEarnings());
            stmt.setDouble(6, payment.getCompanyShare());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update Payment Verification by Driver
    public boolean verifyCashPayment(int paymentId) {
        String query = "UPDATE payments SET verified_by_driver = 'YES' WHERE payment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update Online Transfer Verification by Admin/Manager
    public boolean verifyOnlineTransfer(int paymentId, String status) {
        String query = "UPDATE payments SET verification_status = ? WHERE payment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
