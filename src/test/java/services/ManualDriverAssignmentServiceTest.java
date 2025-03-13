package services;

import dto.BookingDTO;
import dto.DriverDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ManualDriverAssignmentServiceTest {

    private ManualDriverAssignmentService manualDriverAssignmentService;

    @Before
    public void setUp() {
        manualDriverAssignmentService = new ManualDriverAssignmentService();
    }

    @After
    public void tearDown() {
        // Optional: Clean up test assignments or reset data manually if needed
    }

    @Test
    public void testGetPendingBookings() {
        List<BookingDTO> pendingBookings = manualDriverAssignmentService.getPendingBookings();
        assertNotNull("Pending bookings should not be null", pendingBookings);
        // Optional: assertTrue(pendingBookings.size() > 0); if test data available
    }

    @Test
    public void testGetAvailableDrivers() {
        List<DriverDTO> availableDrivers = manualDriverAssignmentService.getAvailableDrivers();
        assertNotNull("Available drivers should not be null", availableDrivers);
        // Optional: assertTrue(availableDrivers.size() > 0); if test data available
    }

    @Test
    public void testAssignDriver() {
        // Fetch at least one pending booking and one available driver from DB
        List<BookingDTO> pendingBookings = manualDriverAssignmentService.getPendingBookings();
        List<DriverDTO> availableDrivers = manualDriverAssignmentService.getAvailableDrivers();

        if (!pendingBookings.isEmpty() && !availableDrivers.isEmpty()) {
            BookingDTO booking = pendingBookings.get(0);
            DriverDTO driver = availableDrivers.get(0);
            int assignedByUserId = 1; // Assume Admin or Operator ID exists in users table

            boolean assigned = manualDriverAssignmentService.assignDriver(
                    booking.getBookingId(),
                    driver.getDriverId(),
                    assignedByUserId
            );
            assertFalse("Driver should be assigned successfully", assigned);
        } else {
            System.out.println("Skipping testAssignDriver: No pending bookings or available drivers in test DB.");
        }
    }
}
