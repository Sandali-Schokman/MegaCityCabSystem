package controllers;

import dto.BookingDTO;
import dto.ReviewDTO;
import models.Booking;
import services.BookingService;
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
    private BookingService bookingService = new BookingService();

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
                response.sendRedirect(request.getContextPath() + "/review?action=review&message=Review submitted successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/review?action=review&error=Failed to submit review.");
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
        String action = request.getParameter("action");

        if (session == null || session.getAttribute("role") == null ) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        if(action.equals("review")) {
            List<BookingDTO> bookings = bookingService.getCompletedBookings();
            request.setAttribute("completedBookings", bookings);
            request.getRequestDispatcher("/views/customer/review.jsp").forward(request, response);
        }
        else if(action.equals("admin_view")) {
            try {
                List<ReviewDTO> reviews = reviewService.getDriverReviews(0);
                request.setAttribute("reviews", reviews);
            } catch (Exception e) {
                request.setAttribute("error", "/views/admin/driver-reviews.jsp?error=Invalid driver ID.");
                e.printStackTrace();
            }

            // Forward to JSP page
            request.getRequestDispatcher("/views/admin/driver-reviews.jsp").forward(request, response);
        }




        try {
        String driver = (request.getParameter("user_id"));
        int driverId = Integer.parseInt(driver);
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

