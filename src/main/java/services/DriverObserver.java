package services;

// Observer Pattern: Notify Admin when driver goes OFF
public interface DriverObserver {
    void notifyAdmin(int driverId, String status);
}