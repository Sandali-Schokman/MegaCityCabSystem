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
import java.util.List;

@WebServlet("/earningsReport")
public class EarningsReportServlet extends HttpServlet {
    private EarningsService earningsService = new EarningsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");

        // Ensure only ADMIN and MANAGER can access reports
        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        // Fetch all drivers' earnings
        List<EarningsDTO> earningsList = earningsService.getAllDriversEarnings();
        request.setAttribute("earningsList", earningsList);

        // Forward to the correct dashboard
        if ("ADMIN".equals(role)) {
            request.getRequestDispatcher("/views/admin/earnings-report.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/views/manager/earnings-report.jsp").forward(request, response);
        }
    }
}
