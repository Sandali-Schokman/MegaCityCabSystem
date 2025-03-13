package controllers;

import dto.PaymentDTO;
import jakarta.servlet.http.HttpSession;
import services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/verifyCash")
public class VerifyCashServlet extends HttpServlet {
    private final PaymentService paymentService = new PaymentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"DRIVER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized+access.");
            return;
        }
        try {
            int paymentId = Integer.parseInt(request.getParameter("payment_id"));

            boolean verified = paymentService.verifyCashPayment(paymentId);

            if (verified) {
                response.sendRedirect(request.getContextPath() + "/verifyCash?message=Cash+payment+verified+successfully.");
            } else {
                response.sendRedirect(request.getContextPath() + "/verifyCash?error=Verification+failed.");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/verifyCash.jsp?error=Invalid+payment+ID.");
        }catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/verifyCash?error=Unexpected+error+occurred.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"DRIVER".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Unauthorized+access.");
            return;
        }

        try {
            Integer driverId = (Integer) session.getAttribute("user_id");//
            if (driverId == null) {
                response.sendRedirect(request.getContextPath() + "/views/login.jsp?error=Session+expired+or+Driver+ID+missing.");
                return;
            }
            System.out.println("DEBUG: Driver ID from session: " + driverId);


            // Strategy Pattern → PaymentService handles filtering logic
            List<PaymentDTO> payments = paymentService.getPendingCashPaymentsForDriver(driverId);

            // DEBUG
            System.out.println("DEBUG: Payments fetched: " + payments.size());

            request.setAttribute("payments", payments);

            // Forward to JSP
            request.getRequestDispatcher("/views/driver/verify-cash.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/views/driver/verify-cash.jsp?error=Unable+to+load+payments.");
        }
    }
}

