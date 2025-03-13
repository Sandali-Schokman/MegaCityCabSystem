package dao;

import config.DatabaseConnection;
import dto.ReviewDTO;
import mappers.ReviewMapper;
import models.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private Connection connection;

    public ReviewDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton Pattern
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new review
    public boolean addReview(ReviewDTO reviewDTO) {
        String query = "INSERT INTO reviews (booking_id, customer_id, driver_id, rating, feedback) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, reviewDTO.getBookingId());
            stmt.setInt(2, reviewDTO.getCustomerId());
            stmt.setInt(3, reviewDTO.getDriverId());
            stmt.setInt(4, reviewDTO.getRating());
            stmt.setString(5, reviewDTO.getFeedback());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all reviews for a driver
    public List<ReviewDTO> getReviewsByDriverId(int driverId) {
        List<ReviewDTO> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews WHERE driver_id = ? ORDER BY review_date DESC";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getInt("rating"),
                        rs.getString("feedback"),
                        rs.getTimestamp("review_date")
                );
                reviews.add(ReviewMapper.toDTO(review));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Get the average rating for a driver
    public double getAverageRating(int driverId) {
        String query = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE driver_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Default if no reviews
    }

    public boolean deleteReview(int reviewId) {
        String query = "DELETE FROM reviews WHERE review_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reviewId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReviewDTO> getAllReviews() {
        List<ReviewDTO> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews ORDER BY review_date DESC";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getInt("rating"),
                        rs.getString("feedback"),
                        rs.getTimestamp("review_date")
                );
                reviews.add(ReviewMapper.toDTO(review));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
