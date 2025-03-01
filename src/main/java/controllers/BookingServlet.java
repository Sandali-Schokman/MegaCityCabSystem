package controllers;

import dto.BookingDTO;
import services.BookingService;
import utils.SessionUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (SessionUtils.isUserLoggedIn(request) || !"CUSTOMER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        int customerId = (int) session.getAttribute("user_id");
        String pickupLocation = request.getParameter("pickup_location");
        String dropoffLocation = request.getParameter("dropoff_location");
        Timestamp scheduledTime = Timestamp.valueOf(request.getParameter("scheduled_time"));
        double fare = Double.parseDouble(request.getParameter("fare"));

        BookingDTO bookingDTO = new BookingDTO(0, customerId, null, pickupLocation, dropoffLocation,
                scheduledTime, "PENDING", fare, "UNPAID", null, null);

        boolean isCreated = bookingService.createBooking(bookingDTO);
        if (isCreated) {
            response.sendRedirect(request.getContextPath() + "/views/dashboards/customer-dashboard.jsp?message=Booking successful.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/booking.jsp?error=Booking failed.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/views/admin/bookings.jsp").forward(request, response);
    }
}
