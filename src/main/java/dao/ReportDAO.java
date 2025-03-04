package dao;

import config.DatabaseConnection;
import dto.BookingTrendsDTO;
import dto.PerformanceReportDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private Connection connection;

    public ReportDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton Pattern
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get Booking Trends
    public List<BookingTrendsDTO> getBookingTrends() {
        List<BookingTrendsDTO> trends = new ArrayList<>();
        String query = "SELECT DATE(scheduled_time) AS date, COUNT(*) AS total, " +
                "SUM(CASE WHEN booking_status = 'COMPLETED' THEN 1 ELSE 0 END) AS completed, " +
                "SUM(CASE WHEN booking_status = 'CANCELLED' THEN 1 ELSE 0 END) AS cancelled " +
                "FROM bookings GROUP BY DATE(scheduled_time) ORDER BY DATE(scheduled_time) DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                trends.add(new BookingTrendsDTO(
                        rs.getString("date"),
                        rs.getInt("total"),
                        rs.getInt("completed"),
                        rs.getInt("cancelled")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trends;
    }

    // Get Driver Performance Report
    public List<PerformanceReportDTO> getDriverPerformance() {
        List<PerformanceReportDTO> reports = new ArrayList<>();
        String query = "SELECT d.driver_id, u.full_name AS driver_name, d.completed_rides, d.total_earnings, " +
                "(SELECT AVG(rating) FROM reviews WHERE driver_id = d.driver_id) AS avg_rating " +
                "FROM drivers d JOIN users u ON d.user_id = u.user_id ORDER BY d.completed_rides DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reports.add(new PerformanceReportDTO(
                        rs.getInt("driver_id"),
                        rs.getString("driver_name"),
                        rs.getInt("completed_rides"),
                        rs.getDouble("total_earnings"),
                        rs.getDouble("avg_rating")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
}
