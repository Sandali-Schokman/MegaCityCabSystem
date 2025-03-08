package services;

import dao.PaymentDAO;
import dto.PaymentDTO;

public class PaymentService {
    private final PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
    }

    public boolean createPayment(PaymentDTO payment) {
        return paymentDAO.createPayment(payment);
    }

    public boolean verifyCashPayment(int paymentId) {
        return paymentDAO.verifyCashPayment(paymentId);
    }

    public boolean verifyOnlineTransfer(int paymentId, String status) {
        return paymentDAO.verifyOnlineTransfer(paymentId, status);
    }
}
