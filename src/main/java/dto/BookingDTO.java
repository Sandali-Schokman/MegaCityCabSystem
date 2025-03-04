package dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BookingDTO {
    private int bookingId;
    private int customerId;
    private Integer driverId;
    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime scheduledTime;
    private String bookingStatus;
    private double fare;
    private String paymentStatus;
    private Timestamp assignedTime;
    private Timestamp completionTime;

    public BookingDTO(int bookingId, int customerId, Integer driverId, String pickupLocation,
                      String dropoffLocation, LocalDateTime scheduledTime, String bookingStatus,
                      double fare, String paymentStatus, Timestamp assignedTime, Timestamp completionTime) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.scheduledTime = scheduledTime;
        this.bookingStatus = bookingStatus;
        this.fare = fare;
        this.paymentStatus = paymentStatus;
        this.assignedTime = assignedTime;
        this.completionTime = completionTime;
    }

    // Getters & Setters (same as Booking model)
    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public Integer getDriverId() { return driverId; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public String getBookingStatus() { return bookingStatus; }
    public double getFare() { return fare; }
    public String getPaymentStatus() { return paymentStatus; }
    public Timestamp getAssignedTime() { return assignedTime; }
    public Timestamp getCompletionTime() { return completionTime; }
}
