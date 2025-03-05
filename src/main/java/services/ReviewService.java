package services;

import dao.ReviewDAO;
import dto.ReviewDTO;

import java.util.List;

public class ReviewService {
    private ReviewDAO reviewDAO = new ReviewDAO(); // Dependency Injection

    // Submit a new review
    public boolean submitReview(int bookingId, int customerId, int driverId, int rating, String feedback) {
        ReviewDTO reviewDTO = new ReviewDTO(0, bookingId, customerId, driverId, rating, feedback, null);
        return reviewDAO.addReview(reviewDTO);
    }

    // Get reviews for a driver
    public List<ReviewDTO> getDriverReviews(int driverId) {
        return reviewDAO.getReviewsByDriverId(driverId);
    }

    // Get average rating for a driver
    public double getDriverAverageRating(int driverId) {
        return reviewDAO.getAverageRating(driverId);
    }

    //Remove Inappropriate Reviews
    public boolean deleteReview(int reviewId) {
        return reviewDAO.deleteReview(reviewId);
    }

}
