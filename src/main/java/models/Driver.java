package models;

public class Driver {
    private int driverId;
    private int userId;
    private int carId;
    private String availability;
    private double totalEarnings;

    public Driver(int driverId, int userId, int carId, String availability, double totalEarnings) {
        this.driverId = driverId;
        this.userId = userId;
        this.carId = carId;
        this.availability = availability;
        this.totalEarnings = totalEarnings;
    }

    public int getDriverId() { return driverId; }
    public int getUserId() { return userId; }
    public int getCarId() { return carId; }
    public String getAvailability() { return availability; }
    public double getTotalEarnings() { return totalEarnings; }

    public void setAvailability(String availability) { this.availability = availability; }
    public void addEarnings(double amount) { this.totalEarnings += amount; }
}
