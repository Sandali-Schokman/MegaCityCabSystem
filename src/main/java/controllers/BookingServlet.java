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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();
    private static final Logger logger = Logger.getLogger(BookingServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (SessionUtils.isUserLoggedIn(request) || !"CUSTOMER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            int customerId = (int) session.getAttribute("user_id");
            String pickupLocation = request.getParameter("pickup_location");
            String dropoffLocation = request.getParameter("dropoff_location");
            String scheduledTimeString = request.getParameter("scheduled_time");
            double fare = Double.parseDouble(request.getParameter("fare"));

            System.out.println("Received scheduled_time: " + scheduledTimeString);

            Timestamp scheduledTime = null;
            if (scheduledTimeString != null && !scheduledTimeString.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime localDateTime = LocalDateTime.parse(scheduledTimeString, formatter);
                    scheduledTime = Timestamp.valueOf(localDateTime);
                    System.out.println("Parsed scheduled_time: " + scheduledTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: scheduled_time is null or empty.");
            }


            // Create booking DTO
            BookingDTO bookingDTO = new BookingDTO(0, customerId, null, pickupLocation, dropoffLocation,
                    scheduledTime.toLocalDateTime(), "PENDING", fare, "UNPAID", null, null);

            boolean isCreated = bookingService.createBooking(bookingDTO);
            if (isCreated) {
                response.sendRedirect(request.getContextPath() + "/views/dashboards/customer-dashboard.jsp?message=Booking successful.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/booking.jsp?error=Booking failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error processing booking: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/views/booking.jsp?error=Invalid booking details.");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/views/admin/bookings.jsp").forward(request, response);
    }
}