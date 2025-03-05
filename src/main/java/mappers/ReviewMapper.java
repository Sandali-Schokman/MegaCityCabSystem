package mappers;

import dto.ReviewDTO;
import models.Review;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getBookingId(),
                review.getCustomerId(),
                review.getDriverId(),
                review.getRating(),
                review.getFeedback(),
                review.getReviewDate()
        );
    }

    public static Review toEntity(ReviewDTO dto) {
        return new Review(
                dto.getReviewId(),
                dto.getBookingId(),
                dto.getCustomerId(),
                dto.getDriverId(),
                dto.getRating(),
                dto.getFeedback(),
                dto.getReviewDate()
        );
    }
}
