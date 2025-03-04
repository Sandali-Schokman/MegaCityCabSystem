package dto;

public class PerformanceReportDTO {
    private int driverId;
    private String driverName;
    private int completedRides;
    private double totalEarnings;
    private double averageRating;

    public PerformanceReportDTO(int driverId, String driverName, int completedRides, double totalEarnings, double averageRating) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.completedRides = completedRides;
        this.totalEarnings = totalEarnings;
        this.averageRating = averageRating;
    }

    public int getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public int getCompletedRides() { return completedRides; }
    public double getTotalEarnings() { return totalEarnings; }
    public double getAverageRating() { return averageRating; }
}
