package controllers;

import dto.BookingDTO;
import dto.DriverDTO;
import services.ManualDriverAssignmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/assignManualDriver")
public class ManualDriverAssignmentServlet extends HttpServlet {
    private final ManualDriverAssignmentService assignmentService = new ManualDriverAssignmentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Ensure session exists and user is either ADMIN or MANAGER
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");


        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        List<BookingDTO> pendingBookings = assignmentService.getPendingBookings();
        List<DriverDTO> availableDrivers = assignmentService.getAvailableDrivers();

        request.setAttribute("pendingBookings", pendingBookings);
        request.setAttribute("availableDrivers", availableDrivers);
        request.getRequestDispatcher("/views/admin/manual-driver-assignment.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Ensure session exists and user is either ADMIN or MANAGER
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        int bookingId = Integer.parseInt(request.getParameter("booking_id"));
        int driverId = Integer.parseInt(request.getParameter("driver_id"));
        int assignedBy = (int) session.getAttribute("user_id");

        boolean success = assignmentService.assignDriver(bookingId, driverId, assignedBy);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/views/admin/manual-driver-assignment.jsp?message=Driver assigned successfully.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/admin/manual-driver-assignment.jsp?error=Assignment failed.");
        }
    }
}
