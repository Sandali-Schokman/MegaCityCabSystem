package controllers;

import services.CommissionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to update commission percentage by Admin
 */
@WebServlet("/updateCommission")
public class UpdateCommissionServlet extends HttpServlet {
    private  CommissionService commissionService = new CommissionService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            double newCommission = Double.parseDouble(request.getParameter("commission"));
            boolean updated = commissionService.updateCommission(newCommission);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/views/dashboards/admin-dashboard.jsp?message=Commission Updated Successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/dashboards/admin-dashboard.jsp?error=Failed to update commission.");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/dashboards/admin-dashboard.jsp?error=Invalid input.");
        }
    }
}
