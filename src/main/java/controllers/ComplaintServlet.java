package controllers;

import dto.ComplaintDTO;
import services.ComplaintService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/complaints")
public class ComplaintServlet extends HttpServlet {
    private final ComplaintService complaintService = new ComplaintService();

    /**
     * Handles retrieving complaints (Admin/Manager access)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");

        // Only Admin & Manager can view complaints
        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        List<ComplaintDTO> complaints = complaintService.getAllComplaints();
        request.setAttribute("complaints", complaints);
        request.getRequestDispatcher("/views/admin/manage-complaints.jsp").forward(request, response);
    }

    /**
     * Handles submitting a new complaint (Customer access)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("role") == null || !"CUSTOMER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        int customerId = (Integer) session.getAttribute("user_id");
        int bookingId = Integer.parseInt(request.getParameter("booking_id"));
        String complaintText = request.getParameter("complaint_text");

        ComplaintDTO complaint = new ComplaintDTO(0, customerId, null, bookingId, complaintText, "PENDING", null);

        if (complaintService.registerComplaint(complaint)) {
            response.sendRedirect(request.getContextPath() + "/views/customer/complaint.jsp?message=Complaint submitted successfully.");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/customer/complaint.jsp?error=Failed to submit complaint.");
        }
    }
}
