package controllers;

import dto.BookingDTO;
import dto.DriverDTO;
import jakarta.servlet.ServletConfig;
import services.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    //Handle POST requests (Create Booking)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        //Session validation
        if (session == null || session.getAttribute("role") == null || !"CUSTOMER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            //Retrieve form parameters
            int customerId = (Integer) session.getAttribute("user_id");
            String pickupLocation = request.getParameter("pickup_location");
            String dropoffLocation = request.getParameter("dropoff_location");

            // Validate Scheduled Time
            String scheduledTimeStr = request.getParameter("scheduled_time");
            if (scheduledTimeStr == null || scheduledTimeStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Scheduled time is required.");
                return;
            }

            // Validate Fare
            String fareStr = request.getParameter("fare");
            System.out.println("DEBUG: Received Fare - " + fareStr); // Debugging

            if (fareStr == null || fareStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Fare is required.");
                return;
            }
            double fare;

            double fare2 = bookingService.calculateFare(pickupLocation, dropoffLocation, 12);
            System.out.println("DEBUG: Received Fare2 -booking servlet 79 " + fare2);

            Timestamp scheduledTime;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(scheduledTimeStr);
                scheduledTime = new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                System.out.println("ERROR: Could not parse scheduled time: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Invalid date format. Use YYYY-MM-DD HH:MM.");
                return;
            }

            //Create Booking DTO (Driver ID is 0 initially, assigned later)
            BookingDTO newBooking = new BookingDTO(0, customerId, 0, pickupLocation, dropoffLocation, scheduledTime, "PENDING", fare2 , "UNPAID");


            // Call service method to save booking
            boolean isBooked = bookingService.createBooking(newBooking);
            if (isBooked) {
                response.sendRedirect(request.getContextPath() + "/views/customer/bookings.jsp?message=Booking Successful.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Booking Failed.");
            }

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=An unexpected error occurred.");
            e.printStackTrace(); // Debugging purposes
        }
    }

    //
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("booking initialized");

    }


    //Handle GET requests (View Bookings)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        List<BookingDTO> bookings = bookingService.getAllBookings();
        request.setAttribute("bookings", "bookings");

        String role = (String) session.getAttribute("role");
        int userId = (Integer) session.getAttribute("user_id");

        System.out.println("DEBUG: Role - " + role);

        // If Customer → Show only their bookings
        if ("CUSTOMER".equals(role)) {
            List<BookingDTO> customerBookings = bookingService.getBookingsByCustomer(userId);
            System.out.println("DEBUG: Customer has " + customerBookings.size() + " bookings.");
            request.setAttribute("bookings", customerBookings);
            request.getRequestDispatcher("/views/customer/bookings.jsp").forward(request, response);
            return;
        }

        // If Admin/Manager → Show all bookings
        if ("ADMIN".equals(role) || "MANAGER".equals(role)) {
            List<BookingDTO> allBookings = bookingService.getAllBookings();
            System.out.println("DEBUG: Admin found " + allBookings.size() + " bookings.");
            request.setAttribute("bookings", allBookings);
            request.getRequestDispatcher("/views/admin/manage-bookings.jsp").forward(request, response);
        }
    }

    //Handle DELETE (Cancel Booking)
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"CUSTOMER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("booking_id"));
        boolean isCancelled = bookingService.cancelBooking(bookingId);

        if (isCancelled) {
            response.sendRedirect(request.getContextPath() + "/views/customer/bookings.jsp?message=Booking Cancelled.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/customer/bookings.jsp?error=Cancellation Failed.");
        }

    }
}