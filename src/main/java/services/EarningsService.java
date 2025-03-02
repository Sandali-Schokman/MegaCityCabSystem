package services;

import dao.EarningsDAO;
import dto.EarningsDTO;

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
}
