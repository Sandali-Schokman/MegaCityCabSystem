package controllers;

import services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/verifyOnlineTransfer")
public class VerifyOnlineTransferServlet extends HttpServlet {
    private final PaymentService paymentService = new PaymentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null ||
                (!"ADMIN".equals(session.getAttribute("role")) && !"MANAGER".equals(session.getAttribute("role")))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        try {
            int paymentId = Integer.parseInt(request.getParameter("payment_id"));
            String status = request.getParameter("status");

            boolean updated = paymentService.verifyOnlineTransfer(paymentId, status);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/views/manager/verify-transfers.jsp?message=Online transfer verification updated.");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/manager/verify-transfers.jsp?error=Verification update failed.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/verify-transfers.jsp?error=Invalid input.");
        }
    }
}
