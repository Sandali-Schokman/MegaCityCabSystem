package services;

// Strategy Pattern: Handles "Off Duty" status updates
public class OffDutyStrategy implements DriverAvailabilityStrategy {
    @Override
    public String updateStatus() {
        return "OFF";
    }
}
