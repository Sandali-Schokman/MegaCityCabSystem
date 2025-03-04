package controllers;

import dto.BookingTrendsDTO;
import services.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/bookingTrends")
public class BookingTrendsServlet extends HttpServlet {
    private ReportService reportService = new ReportService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        String role = (String) session.getAttribute("role");

        if (!"ADMIN".equals(role) && !"MANAGER".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/views/unauthorized.jsp");
            return;
        }

        List<BookingTrendsDTO> trends = reportService.getBookingTrends();
        request.setAttribute("trends", trends);
        request.getRequestDispatcher("/views/admin/booking-trends.jsp").forward(request, response);
    }
}
