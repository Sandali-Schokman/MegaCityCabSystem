package services;

import dao.BookingDAO;
import dao.DriverDAO;
import dto.BookingDTO;
import dto.DriverDTO;

import java.util.List;

public class ManualDriverAssignmentService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final DriverDAO driverDAO = new DriverDAO();

    public List<BookingDTO> getPendingBookings() {
        return bookingDAO.getPendingBookings();
    }

    public List<DriverDTO> getAvailableDrivers() {
        return driverDAO.getAvailableDrivers();
    }

    public boolean assignDriver(int bookingId, int driverId, int assignedBy) {
        return bookingDAO.assignDriverToBooking(bookingId, driverId, assignedBy) &&
                driverDAO.updateDriverAvailability(driverId, "IN_A_HIRE");
    }
}
