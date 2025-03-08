package dto;

import java.sql.Timestamp;

//DTO for transferring Payment data between layers

public class PaymentDTO {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String method;
    private String paymentStatus;
    private Timestamp paymentDate;
    private String verifiedByDriver;
    private String verificationStatus;
    private double driverEarnings;
    private double companyShare;

    public PaymentDTO(int paymentId, int bookingId, double amount, String method, String paymentStatus,
                      Timestamp paymentDate, String verifiedByDriver, String verificationStatus,
                      double driverEarnings, double companyShare) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.verifiedByDriver = verifiedByDriver;
        this.verificationStatus = verificationStatus;
        this.driverEarnings = driverEarnings;
        this.companyShare = companyShare;
    }

    public int getPaymentId() { return paymentId; }
    public int getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getPaymentStatus() { return paymentStatus; }
    public Timestamp getPaymentDate() { return paymentDate; }
    public String getVerifiedByDriver() { return verifiedByDriver; }
    public String getVerificationStatus() { return verificationStatus; }
    public double getDriverEarnings() { return driverEarnings; }
    public double getCompanyShare() { return companyShare; }
}
