package controllers;

import services.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/endBooking")
public class BookingCompletionServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService(); // Facade to BookingDAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"DRIVER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized+access.");
            return;
        }

        try {
            int bookingId = Integer.parseInt(request.getParameter("booking_id"));
            int driverId = (Integer) session.getAttribute("user_id");

            boolean ended = bookingService.endBookingByDriver(bookingId, driverId);

            if (ended) {
                response.sendRedirect(request.getContextPath() + "/views/driver/driver-bookings.jsp?message=Booking+marked+as+completed.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/driver/driver-bookings.jsp?error=Could+not+end+booking.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/views/driver/driver-bookings.jsp?error=Invalid+booking+ID.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // You can use this for testing or redirecting with instructions
        response.sendRedirect(request.getContextPath()
                + "/views/driver/driver-bookings.jsp?message=Use+End+Booking+form+button+to+complete+booking.");
    }
}
