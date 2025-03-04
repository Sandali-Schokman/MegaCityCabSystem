package dao;

import config.DatabaseConnection;
import dto.ComplaintDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    // Register a new complaint
    public boolean registerComplaint(ComplaintDTO complaint) {
        String query = "INSERT INTO complaints (customer_id, booking_id, complaint_text, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, complaint.getCustomerId());
            stmt.setInt(2, complaint.getBookingId());
            stmt.setString(3, complaint.getComplaintText());
            stmt.setString(4, complaint.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all complaints
    public List<ComplaintDTO> getAllComplaints() {
        List<ComplaintDTO> complaints = new ArrayList<>();
        String query = "SELECT c.complaint_id, c.customer_id, u.full_name AS customer_name, c.booking_id, c.complaint_text, c.status " +
                "FROM complaints c " +
                "JOIN users u ON c.customer_id = u.user_id " +
                "ORDER BY c.submitted_at DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                complaints.add(new ComplaintDTO(
                        rs.getInt("complaint_id"),
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getInt("booking_id"),
                        rs.getString("complaint_text"),
                        rs.getString("status"),
                        rs.getTimestamp("submitted_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    // Retrieve complaints by status (Pending, Resolved)
    public List<ComplaintDTO> getComplaintsByStatus(String status) {
        List<ComplaintDTO> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints WHERE status = ? ORDER BY submitted_at DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                complaints.add(new ComplaintDTO(
                        rs.getInt("complaint_id"),
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getInt("booking_id"),
                        rs.getString("complaint_text"),
                        rs.getString("status"),
                        rs.getTimestamp("submitted_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    // Update complaint status
    public boolean updateComplaintStatus(int complaintId, String newStatus) {
        String query = "UPDATE complaints SET status = ? WHERE complaint_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, complaintId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
