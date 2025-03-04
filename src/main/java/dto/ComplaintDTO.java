package dto;

import java.sql.Timestamp;

public class ComplaintDTO {
    private int complaintId;
    private int customerId;
    private String customerName;
    private int bookingId;
    private String complaintText;
    private String status;
    private Timestamp submittedAt;

    public ComplaintDTO(int complaintId, int customerId, String customerName, int bookingId, String complaintText, String status, Timestamp submittedAt) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.bookingId = bookingId;
        this.complaintText = complaintText;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    // Getters and Setters
    public int getComplaintId() { return complaintId; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public int getBookingId() { return bookingId; }
    public String getComplaintText() { return complaintText; }
    public String getStatus() { return status; }
    public Timestamp getSubmittedAt() { return submittedAt; }
}

