package services;

import dao.DriverDAO;
import dto.DriverDTO;
import java.util.List;


public class DriverAvailabilityService {      //Facade Pattern
    private DriverDAO driverDAO = new DriverDAO();

    public boolean updateAvailability(int driverId, DriverAvailabilityStrategy strategy) {
        String newStatus = strategy.updateStatus();
        return driverDAO.updateDriverAvailability(driverId, newStatus);
    }

    // ✅ Fetch all drivers for Admin & Manager dashboards
    public List<DriverDTO> getAllDrivers() {
        return driverDAO.getAllDrivers();
    }
}
