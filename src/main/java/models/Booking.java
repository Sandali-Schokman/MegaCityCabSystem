package models;

import java.sql.Timestamp;


/**
 * Booking Entity - Represents a booking in the system.
 * Implements Builder Pattern for flexible object creation.
 */

public class Booking {
    private int bookingId;
    private int customerId;
    private Integer driverId; // Nullable before assignment
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

    // Constructor- Builder Pattern
    private Booking(BookingBuilder builder) {
        this.bookingId = builder.bookingId;
        this.customerId = builder.customerId;
        this.driverId = builder.driverId;
        this.pickupLocation = builder.pickupLocation;
        this.dropoffLocation = builder.dropoffLocation;
        this.scheduledTime = builder.scheduledTime;
        this.bookingStatus = builder.bookingStatus;
        this.fare = builder.fare;
        this.paymentStatus = builder.paymentStatus;
        this.assignedBy = builder.assignedBy;
        this.assignedByUser = builder.assignedByUser;
        this.assignedTime = builder.assignedTime;
        this.completionTime = builder.completionTime;
    }

    // Getters (No Setters - Immutable Object)
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

    // Builder Class for Booking
    public static class BookingBuilder {
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

        public BookingBuilder bookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public BookingBuilder customerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public BookingBuilder driverId(Integer driverId) {
            this.driverId = driverId;
            return this;
        }

        public BookingBuilder pickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public BookingBuilder dropoffLocation(String dropoffLocation) {
            this.dropoffLocation = dropoffLocation;
            return this;
        }

        public BookingBuilder scheduledTime(Timestamp scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public BookingBuilder bookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public BookingBuilder fare(double fare) {
            this.fare = fare;
            return this;
        }

        public BookingBuilder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public BookingBuilder assignedTime(Timestamp assignedTime) {
            this.assignedTime = assignedTime;
            return this;
        }

        public BookingBuilder completionTime(Timestamp completionTime) {
            this.completionTime = completionTime;
            return this;
        }

        public BookingBuilder assignedBy(String assignedBy) {
            this.assignedBy = assignedBy;
            return this;
        }

        public BookingBuilder assignedByUser(Integer assignedByUser) {
            this.assignedByUser = assignedByUser;
            return this;
        }

        public BookingBuilder setBookingId(int bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public BookingBuilder setCustomerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public BookingBuilder setDriverId(Integer driverId) {
            this.driverId = driverId;
            return this;
        }

        public BookingBuilder setPickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public BookingBuilder setDropoffLocation(String dropoffLocation) {
            this.dropoffLocation = dropoffLocation;
            return this;
        }

        public BookingBuilder setScheduledTime(Timestamp scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public BookingBuilder setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public BookingBuilder setFare(double fare) {
            this.fare = fare;
            return this;
        }

        public BookingBuilder setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public BookingBuilder setAssignedBy(String assignedBy) {
            this.assignedBy = assignedBy;
            return this;
        }

        public BookingBuilder setAssignedByUser(Integer assignedByUser) {
            this.assignedByUser = assignedByUser;
            return this;
        }

        public BookingBuilder setAssignedTime(Timestamp assignedTime) {
            this.assignedTime = assignedTime;
            return this;
        }

        public BookingBuilder setCompletionTime(Timestamp completionTime) {
            this.completionTime = completionTime;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }

}
