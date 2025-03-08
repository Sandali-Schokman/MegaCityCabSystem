package controllers;

import services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verifyCash")
public class VerifyCashServlet extends HttpServlet {
    private final PaymentService paymentService = new PaymentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int paymentId = Integer.parseInt(request.getParameter("payment_id"));
            boolean verified = paymentService.verifyCashPayment(paymentId);

            if (verified) {
                response.sendRedirect(request.getContextPath() + "/views/driver/verify-cash.jsp?message=Cash payment verified successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/driver/verify-cash.jsp?error=Verification failed.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/driver/verify-cash.jsp?error=Invalid input.");
        }
    }
}
