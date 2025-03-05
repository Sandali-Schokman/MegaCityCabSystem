package dto;

import java.sql.Timestamp;

public class ReviewDTO {
    private int reviewId;
    private int bookingId;
    private int customerId;
    private int driverId;
    private int rating;
    private String feedback;
    private Timestamp reviewDate;

    public ReviewDTO(int reviewId, int bookingId, int customerId, int driverId, int rating, String feedback, Timestamp reviewDate) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.rating = rating;
        this.feedback = feedback;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() { return reviewId; }
    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public int getDriverId() { return driverId; }
    public int getRating() { return rating; }
    public String getFeedback() { return feedback; }
    public Timestamp getReviewDate() { return reviewDate; }
}
