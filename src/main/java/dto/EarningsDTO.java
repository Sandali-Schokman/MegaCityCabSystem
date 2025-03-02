package dto;

import java.sql.Timestamp;

public class EarningsDTO {
    private int driverId;
    private double totalEarnings;
    private int completedRides;
    private Timestamp lastPaymentDate;

    public EarningsDTO(int driverId, double totalEarnings, int completedRides, Timestamp lastPaymentDate) {
        this.driverId = driverId;
        this.totalEarnings = totalEarnings;
        this.completedRides = completedRides;
        this.lastPaymentDate = lastPaymentDate;
    }

    public int getDriverId() { return driverId; }
    public double getTotalEarnings() { return totalEarnings; }
    public int getCompletedRides() { return completedRides; }
    public Timestamp getLastPaymentDate() { return lastPaymentDate; }
}
