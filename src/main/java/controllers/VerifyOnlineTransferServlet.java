package controllers;

import dto.PaymentDTO;
import services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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

            String role = (String) session.getAttribute("role");
            String redirectPage = "ADMIN".equals(session.getAttribute("role"))
                    ? "/verifyOnlineTransfer?message=Updated"
                    : "/verifyOnlineTransfer?message=Updated";

            response.sendRedirect(request.getContextPath() + "/views/manager/verify-transfers.jsp");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/verifyOnlineTransfer?error=Invalid input");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null ||
                (!"ADMIN".equals(session.getAttribute("role")) && !"MANAGER".equals(session.getAttribute("role")))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized access.");
            return;
        }

        List<PaymentDTO> payments = paymentService.getPendingBankTransfersForVerification();
        request.setAttribute("payments", payments);
        request.getRequestDispatcher("/views/manager/verify-transfers.jsp").forward(request, response);
    }

}
