package services;

import dao.EarningsDAO;
import dto.EarningsDTO;
import java.util.List;

/**
 * Service class for handling earnings-related operations
 * Follows Facade Pattern to provide simplified API
 */

public class EarningsService {
    private EarningsDAO earningsDAO = new EarningsDAO(); // Dependency Injection

    // Get Driver Earnings Data
    public EarningsDTO getDriverEarnings(int driverId) {
        return earningsDAO.getDriverEarnings(driverId);
    }

    // Add earnings after a completed ride
    public boolean addEarnings(int driverId, double fare) {
        return earningsDAO.updateEarnings(driverId, fare);
    }

    // Get all drivers' earnings (for admin & manager)
    public List<EarningsDTO> getAllDriversEarnings() {
        return earningsDAO.getAllDriversEarnings();
    }
}
