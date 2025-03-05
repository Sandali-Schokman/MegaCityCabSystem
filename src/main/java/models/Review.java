package models;

import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private int bookingId;
    private int customerId;
    private int driverId;
    private int rating;
    private String feedback;
    private Timestamp reviewDate;

    public Review(int reviewId, int bookingId, int customerId, int driverId, int rating, String feedback, Timestamp reviewDate) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.rating = rating;
        this.feedback = feedback;
        this.reviewDate = reviewDate;
    }

    // Getters
    public int getReviewId() { return reviewId; }
    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public int getDriverId() { return driverId; }
    public int getRating() { return rating; }
    public String getFeedback() { return feedback; }
    public Timestamp getReviewDate() { return reviewDate; }

    // Setters
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public void setReviewDate(Timestamp reviewDate) { this.reviewDate = reviewDate; }
}
