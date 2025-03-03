package controllers;

import dto.EarningsDTO;
import services.EarningsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/earnings")
public class EarningsServlet extends HttpServlet {
    private EarningsService earningsService = new EarningsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");

        // Ensure only DRIVERS can access
        if (!"DRIVER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        // Get driver ID safely
        int driverId;
        try {
            driverId = (Integer) session.getAttribute("user_id");
        } catch (ClassCastException e) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Invalid session data.");
            return;
        }

        EarningsDTO earnings = earningsService.getDriverEarnings(driverId);
        request.setAttribute("earnings", earnings);
        request.getRequestDispatcher("/views/driver/earnings.jsp").forward(request, response);
    }
}
