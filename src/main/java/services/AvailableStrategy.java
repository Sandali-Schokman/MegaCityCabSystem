package services;

// Strategy Pattern: Handles "Available" status updates
public class AvailableStrategy implements DriverAvailabilityStrategy {
    @Override
    public String updateStatus() {
        return "AVAILABLE";
    }
}
