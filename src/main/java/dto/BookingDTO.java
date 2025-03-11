package dto;

import java.sql.Timestamp;

/**
 * BookingDTO - Data Transfer Object for Bookings.
 * Used to transfer data between layers.
 */
public class BookingDTO {
    private int bookingId;
    private int customerId;
    private Integer driverId;
    private String pickupLocation;
    private String dropoffLocation;
    private Timestamp scheduledTime;
    private String bookingStatus;
    private double fare;
    private String paymentStatus;
    private String assignedBy;
    private Integer assignedByUser;
    private Timestamp assignedTime;
    private Timestamp completionTime;

    public BookingDTO(int bookingId, int customerId, Integer driverId, String pickupLocation,
                      String dropoffLocation, Timestamp scheduledTime, String bookingStatus,
                      double fare, String paymentStatus, String assignedBy, Integer assignedByUser,
                      Timestamp assignedTime, Timestamp completionTime) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.scheduledTime = scheduledTime;
        this.bookingStatus = bookingStatus;
        this.fare = fare;
        this.paymentStatus = paymentStatus;
        this.assignedBy = assignedBy;
        this.assignedByUser = assignedByUser;
        this.assignedTime = assignedTime;
        this.completionTime = completionTime;
    }

    public BookingDTO(int bookingId, int customerId, int driverId, String pickupLocation, String dropoffLocation, Timestamp scheduledTime, String pending, double fare, String unpaid) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.driverId = driverId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.scheduledTime = scheduledTime;
        this.bookingStatus = pending;
        this.fare = fare;
        this.paymentStatus = unpaid;
    }

    // Getters
    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public Integer getDriverId() { return driverId; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public Timestamp getScheduledTime() { return scheduledTime; }
    public String getBookingStatus() { return bookingStatus; }
    public double getFare() { return fare; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getAssignedBy() { return assignedBy; }
    public Integer getAssignedByUser() { return assignedByUser; }
    public Timestamp getAssignedTime() { return assignedTime; }
    public Timestamp getCompletionTime() { return completionTime; }
}
