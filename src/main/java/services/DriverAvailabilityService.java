package services;

import dao.DriverDAO;

public class DriverAvailabilityService {      //Facade Pattern
    private DriverDAO driverDAO = new DriverDAO();

    public boolean updateAvailability(int driverId, DriverAvailabilityStrategy strategy) {
        String newStatus = strategy.updateStatus();
        return driverDAO.updateDriverAvailability(driverId, newStatus);
    }
}
