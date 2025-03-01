package services;

import dto.DriverDTO;
import java.util.List;
import java.util.Optional;

public class NearestDriverStrategy implements DriverAssignmentStrategy {
    @Override
    public Optional<DriverDTO> assignDriver(List<DriverDTO> availableDrivers) {
        if (availableDrivers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(availableDrivers.get(0)); // For now, just pick the first driver
    }
}
