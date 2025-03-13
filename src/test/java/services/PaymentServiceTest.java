package services;

import dto.PaymentDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class PaymentServiceTest {

    private PaymentService paymentService;

    @Before
    public void setUp() {
        paymentService = new PaymentService();
    }

    @After
    public void tearDown() {
        // Optional cleanup if required
    }


    @Test
    public void testVerifyCashPayment() {
        int paymentId = 1; // Replace with an actual PENDING cash payment ID in DB
        boolean result = paymentService.verifyCashPayment(paymentId);
        assertTrue("Cash payment should be verified successfully", result);
    }

    @Test
    public void testVerifyOnlineTransfer() {
        int paymentId = 2; // Replace with actual payment ID of a pending bank transfer
        String status = "APPROVED";
        boolean result = paymentService.verifyOnlineTransfer(paymentId, status);
        assertTrue("Bank transfer should be verified successfully", result);
    }

    @Test
    public void testGetPendingCashPaymentsForDriver() {
        int driverId = 1; // Must be a valid driver ID
        List<PaymentDTO> payments = paymentService.getPendingCashPaymentsForDriver(driverId);
        assertNotNull("List of pending cash payments should not be null", payments);
        // Optionally check size > 0 if data is present
    }

    @Test
    public void testGetPendingBankTransfersForVerification() {
        List<PaymentDTO> bankTransfers = paymentService.getPendingBankTransfersForVerification();
        assertNotNull("Pending bank transfers list should not be null", bankTransfers);
        // Optionally check size > 0 if data is present
    }
}
