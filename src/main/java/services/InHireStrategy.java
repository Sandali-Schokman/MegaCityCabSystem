package services;

// Strategy Pattern: Handles "In a Hire" status updates
public class InHireStrategy implements DriverAvailabilityStrategy {
    @Override
    public String updateStatus() {
        return "IN_A_HIRE";
    }
}
