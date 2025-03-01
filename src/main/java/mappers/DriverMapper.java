package mappers;

import dto.DriverDTO;
import models.Driver;

public class DriverMapper {
    public static DriverDTO toDTO(Driver driver) {
        return new DriverDTO(driver.getDriverId(), driver.getUserId(), driver.getCarId(), driver.getAvailability(), driver.getTotalEarnings());
    }

    public static Driver toEntity(DriverDTO dto) {
        return new Driver(dto.getDriverId(), dto.getUserId(), dto.getCarId(), dto.getAvailability(), dto.getTotalEarnings());
    }
}

