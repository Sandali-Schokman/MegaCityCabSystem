package controllers;

import dao.BookingDAO;
import dao.DriverDAO;
import dto.BookingDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/driver/assignedRides")
public class DriverAssignedRidesServlet extends HttpServlet {
    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("user_id");

        // Get driverId from DAO using userId
        DriverDAO driverDAO = new DriverDAO();
        int driverId = driverDAO.getDriverByUserId(userId).getDriverId();

        // Fetch assigned bookings
        List<BookingDTO> assignedBookings = bookingDAO.getBookingsByDriverId(driverId);

        // Forward to JSP
        request.setAttribute("assignedBookings", assignedBookings);
        request.getRequestDispatcher("/views/driver/assigned-rides.jsp").forward(request, response);
    }
}
