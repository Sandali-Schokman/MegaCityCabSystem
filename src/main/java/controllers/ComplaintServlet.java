package controllers;

import dto.BookingDTO;
import dto.ComplaintDTO;
import services.BookingService;
import services.ComplaintService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.awt.datatransfer.SystemFlavorMap;
import java.io.IOException;
import java.util.List;

@WebServlet("/complaints")
public class ComplaintServlet extends HttpServlet {
    private final ComplaintService complaintService = new ComplaintService();
    private BookingService bookingService = new BookingService();

    /**
     * Handles retrieving complaints (Admin/Manager access)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String action = request.getParameter("action");

        if (action == null || "add".equalsIgnoreCase(action)) {
            // Customer complaint form
            if (session == null || !"CUSTOMER".equals(session.getAttribute("role"))) {
                response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized+access.");
                return;
            }
            int customerId = (int) session.getAttribute("user_id");
            List<BookingDTO> bookings = bookingService.getCompletedBookingsByCustomer(customerId);
            request.setAttribute("completedBookings", bookings);
            request.getRequestDispatcher("/views/customer/complaint.jsp").forward(request, response);
        } else if ("view".equalsIgnoreCase(action)) {
            // View complaints - Admin or Manager
            if (session == null || session.getAttribute("role") == null) {
                response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized+access.");
                return;
            }

            String role = (String) session.getAttribute("role");
            if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
                return;
            }

            List<ComplaintDTO> complaints = complaintService.getAllComplaints();
            request.setAttribute("complaints", complaints);
            System.out.println("Complaints: " + complaints.size());
            request.getRequestDispatcher("/views/admin/manage-complaints.jsp").forward(request, response);
        }
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
            response.sendRedirect(request.getContextPath() + "/complaints?action=add&message=Complaint submitted successfully.");
        } else {
            response.sendRedirect(request.getContextPath() + "/complaints?action=add&error=Failed to submit complaint.");
        }
    }
}
