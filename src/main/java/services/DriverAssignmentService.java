package services;

import dao.DriverDAO;
import dto.DriverDTO;
import java.util.List;
import java.util.Optional;

public class DriverAssignmentService {
    private final DriverDAO driverDAO = new DriverDAO();
    private DriverAssignmentStrategy assignmentStrategy;

    public DriverAssignmentService(DriverAssignmentStrategy assignmentStrategy) {
        this.assignmentStrategy = assignmentStrategy;
    }

    public Optional<DriverDTO> assignDriver() {
        List<DriverDTO> availableDrivers = driverDAO.getAvailableDrivers();
        return assignmentStrategy.assignDriver(availableDrivers);
    }

    public void setAssignmentStrategy(DriverAssignmentStrategy strategy) {
        this.assignmentStrategy = strategy;
    }
}

