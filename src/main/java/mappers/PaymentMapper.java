package mappers;

import dto.PaymentDTO;
import models.Payment;

public class PaymentMapper {
    public static PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentDate(),
                payment.getVerifiedByDriver(),
                payment.getVerificationStatus(),
                payment.getDriverEarnings(),
                payment.getCompanyShare()
        );
    }

    public static Payment toEntity(PaymentDTO dto) {
        return new Payment(
                dto.getPaymentId(),
                dto.getBookingId(),
                dto.getAmount(),
                dto.getMethod(),
                dto.getPaymentStatus(),
                dto.getPaymentDate(),
                dto.getVerifiedByDriver(),
                dto.getVerificationStatus(),
                dto.getDriverEarnings(),
                dto.getCompanyShare()
        );
    }
}
