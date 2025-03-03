package controllers;

import services.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updateAvailability")
public class DriverAvailabilityServlet extends HttpServlet {
    private DriverAvailabilityService availabilityService = new DriverAvailabilityService();
    private AdminNotifier adminNotifier = new AdminNotifier(); // Observer Pattern

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // **Fix: Correct session validation**
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Session expired. Please log in again.");
            return;
        }


        // **Fix: Ensure user_id exists and is an Integer**
        Object userIdObj = session.getAttribute("user_id");
        if (userIdObj == null || !(userIdObj instanceof Integer)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Session error. Please log in again.");
            return;
        }

        int driverId = (Integer) userIdObj; // Safe type conversion

        // **Fix: Ensure role is set properly**
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("DRIVER")) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        // Get availability status from request
        String status = request.getParameter("status");
        DriverAvailabilityStrategy strategy;

        // Use Strategy Pattern for availability management
        switch (status) {
            case "AVAILABLE":
                strategy = new AvailableStrategy();
                break;
            case "IN_A_HIRE":
                strategy = new InHireStrategy();
                break;
            case "OFF":
                strategy = new OffDutyStrategy();
                adminNotifier.notifyAdmin(driverId, "OFF");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/views/dashboards/driver-dashboard.jsp?error=Invalid status.");
                return;
        }

        if (availabilityService.updateAvailability(driverId, strategy)) {
            response.sendRedirect(request.getContextPath() + "/views/dashboards/driver-dashboard.jsp?message=Status updated.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/dashboards/driver-dashboard.jsp?error=Failed to update status.");
        }
    }
}
