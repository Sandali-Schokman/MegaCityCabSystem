package controllers;

import services.ReviewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteReview")
public class DeleteReviewServlet extends HttpServlet {
    private final ReviewService reviewService = new ReviewService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check Admin or Manager Role
        if (session == null || session.getAttribute("role") == null ||
                (!"ADMIN".equals(session.getAttribute("role")) && !"MANAGER".equals(session.getAttribute("role")))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            int reviewId = Integer.parseInt(request.getParameter("review_id"));
            boolean isDeleted = reviewService.deleteReview(reviewId);

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/review?action=admin_view&message=Review deleted successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/review?action=admin_view&Failed to delete review.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/review?action=admin_view&error=Invalid review ID.");
        }
    }
}
