package services;

import dao.PaymentDAO;
import dto.PaymentDTO;

import java.util.List;

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

    public List<PaymentDTO> getPendingCashPaymentsForDriver(int driverId) {
        return paymentDAO.getPendingCashPaymentsForDriver(driverId);
    }

    public List<PaymentDTO> getPendingBankTransfersForVerification() {
        return paymentDAO.getPendingBankTransfersForVerification();
    }

}
