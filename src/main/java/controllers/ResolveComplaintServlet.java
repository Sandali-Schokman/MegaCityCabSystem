package controllers;

import services.ComplaintService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/resolveComplaint")
public class ResolveComplaintServlet extends HttpServlet {
    private ComplaintService complaintService = new ComplaintService(); // ✅ Business Logic Layer

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if Admin or Manager is logged in
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        // Get Complaint ID from form submission
        int complaintId;
        try {
            complaintId = Integer.parseInt(request.getParameter("complaint_id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/manage-complaints.jsp?error=Invalid complaint ID.");
            return;
        }

        // Mark complaint as resolved
        boolean success = complaintService.resolveComplaint(complaintId);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/views/admin/manage-complaints.jsp?message=Complaint resolved successfully.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/admin/manage-complaints.jsp?error=Failed to resolve complaint.");
        }
    }
}
