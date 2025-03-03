package services;

public class AdminNotifier implements DriverObserver {
    @Override
    public void notifyAdmin(int driverId, String status) {
        if ("OFF".equals(status)) {
            System.out.println("⚠️ Admin Alert: Driver ID " + driverId + " is now OFF.");
        }
    }
}
