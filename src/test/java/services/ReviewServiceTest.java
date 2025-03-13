package services;

import dto.ReviewDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ReviewServiceTest {

    private ReviewService reviewService;

    // Sample test data (adjust these according to your DB test data or insert some manually)
    private int testBookingId = 3;
    private int testCustomerId = 4;
    private int testDriverId = 3;
    private int testRating = 3;
    private String testFeedback = "Test delete review";

    private int insertedReviewId = 0; // will store reviewId if created

    @Before
    public void setUp() {
        reviewService = new ReviewService();
    }

    @After
    public void tearDown() {
        // Clean up inserted review if created
        if (insertedReviewId > 0) {
            reviewService.deleteReview(insertedReviewId);
        }
    }

    @Test
    public void testSubmitReview() {
        boolean result = reviewService.submitReview(testBookingId, testCustomerId, testDriverId, testRating, testFeedback);
        assertTrue("Review submission should return true", result);

        // Optionally verify it was saved by retrieving reviews
        List<ReviewDTO> reviews = reviewService.getDriverReviews(testDriverId);
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());

        // Store insertedReviewId for deletion
        ReviewDTO lastReview = reviews.get(reviews.size() - 1);
        insertedReviewId = lastReview.getReviewId();

        assertEquals("Review feedback should match", testFeedback, lastReview.getFeedback());
    }

    @Test
    public void testGetDriverReviews() {
        List<ReviewDTO> reviews = reviewService.getDriverReviews(testDriverId);
        assertNotNull("Driver reviews list should not be null", reviews);

        for (ReviewDTO review : reviews) {
            assertEquals("All reviews must belong to the driver", testDriverId, review.getDriverId());
        }
    }

    @Test
    public void testGetAllReviewsWhenDriverIdIsZero() {
        List<ReviewDTO> allReviews = reviewService.getDriverReviews(0);
        assertNotNull("All reviews should be fetched when driverId=0", allReviews);
    }

    @Test
    public void testGetDriverAverageRating() {
        double averageRating = reviewService.getDriverAverageRating(testDriverId);
        assertTrue("Average rating should be non-negative", averageRating >= 0.0);
    }

    @Test
    public void testDeleteReview() {
        // First, create a review
        boolean added = reviewService.submitReview(testBookingId, testCustomerId, testDriverId, 3, "Test delete review");
        assertTrue(added);

        // Fetch last review ID
        List<ReviewDTO> reviews = reviewService.getDriverReviews(testDriverId);
        ReviewDTO last = reviews.get(reviews.size() - 1);
        int reviewIdToDelete = last.getReviewId();

        // Try deleting
        boolean deleted = reviewService.deleteReview(reviewIdToDelete);
        assertTrue("Review should be deleted successfully", deleted);
    }
}
