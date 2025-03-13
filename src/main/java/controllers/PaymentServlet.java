package controllers;

import dto.PaymentDTO;
import services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private final PaymentService paymentService = new PaymentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("booking_id"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String method = request.getParameter("method");

            double commission = 10.00 / 100; // Get from commission_settings table later
            double companyShare = amount * commission;
            double driverEarnings = amount - companyShare;

            PaymentDTO payment = new PaymentDTO(0, bookingId, amount, method, "PENDING", null, "YES".equalsIgnoreCase(method) ? "YES" : "NO", "PENDING", driverEarnings, companyShare);
            boolean success = paymentService.createPayment(payment);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/booking?message=Payment Success.");
            } else {

                response.sendRedirect(request.getContextPath() + "/views/customer/payment.jsp?error=Payment Failed.&booking_id=" + bookingId + "&fare="+amount);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/customer/payment.jsp?error=Invalid input.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract booking_id and fare from GET parameters
        try {
            int bookingId = Integer.parseInt(request.getParameter("booking_id"));
            double fare = Double.parseDouble(request.getParameter("fare"));

            // Forward to payment.jsp with values
            request.setAttribute("booking_id", bookingId);
            request.setAttribute("fare", fare);
            request.getRequestDispatcher("/views/customer/payment.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/views/customer/bookings.jsp?error=Invalid Payment Request.");
        }
    }

}
