package controllers;

import dto.ReviewDTO;
import services.ReviewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();

    /**
     * Handle customer review submission (POST request)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null || !"CUSTOMER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            int customerId = (Integer) session.getAttribute("user_id");
            int bookingId = Integer.parseInt(request.getParameter("booking_id"));
            int driverId = Integer.parseInt(request.getParameter("driver_id"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String feedback = request.getParameter("feedback");

            boolean isSubmitted = reviewService.submitReview(bookingId, customerId, driverId, rating, feedback);

            if (isSubmitted) {
                response.sendRedirect(request.getContextPath() + "/views/customer/review-success.jsp?message=Review submitted successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/customer/review.jsp?error=Failed to submit review.");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/customer/review.jsp?error=Invalid input.");
        }
    }

    /**
     * Handle fetching driver reviews (GET request)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null ||
                (!"ADMIN".equals(session.getAttribute("role")) && !"MANAGER".equals(session.getAttribute("role")))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            int driverId = Integer.parseInt(request.getParameter("driver_id"));
            List<ReviewDTO> reviews = reviewService.getDriverReviews(driverId);
            double avgRating = reviewService.getDriverAverageRating(driverId);

            request.setAttribute("reviews", reviews);
            request.setAttribute("avgRating", avgRating);
            request.getRequestDispatcher("/views/admin/driver-reviews.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/driver-reviews.jsp?error=Invalid driver ID.");
        }
    }
}

