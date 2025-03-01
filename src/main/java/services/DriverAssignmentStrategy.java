package services;

import dto.DriverDTO;
import java.util.List;
import java.util.Optional;

public interface DriverAssignmentStrategy {
    Optional<DriverDTO> assignDriver(List<DriverDTO> availableDrivers);
}
