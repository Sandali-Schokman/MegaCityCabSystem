package services;

import dto.BookingDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class BookingServiceTest {

    private BookingService bookingService;
    private BookingDTO sampleBooking;

    @Before
    public void setUp() {
        bookingService = new BookingService();

        // Initialize sampleBooking DTO
        sampleBooking = new BookingDTO();
        sampleBooking.setCustomerId(4);
        sampleBooking.setPickupLocation("Test Pickup");
        sampleBooking.setDropoffLocation("Test Drop");
        sampleBooking.setDistanceKm(10.5);
        sampleBooking.setStatus("Pending");
        sampleBooking.setScheduledTime(new java.sql.Timestamp(System.currentTimeMillis()));
        sampleBooking.setPaymentStatus("UNPAID");
    }

    @After
    public void tearDown() {
        // Optional: Clean test data manually if needed
    }

    @Test
    public void testCreateBooking() {
        boolean result = bookingService.createBooking(sampleBooking);
        assertTrue("Booking should be created successfully", result);
    }

    @Test
    public void testGetAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        assertNotNull(bookings);
    }

    @Test
    public void testGetBookingsByCustomer() {
        List<BookingDTO> customerBookings = bookingService.getBookingsByCustomer(1);
        assertNotNull(customerBookings);
    }

    @Test
    public void testGetBookingById() {
        boolean created = bookingService.createBooking(sampleBooking);
        assertTrue(created);

        List<BookingDTO> all = bookingService.getAllBookings();
        BookingDTO latest = all.get(all.size() - 1);
        Optional<BookingDTO> result = bookingService.getBookingById(latest.getBookingId());

        assertTrue(result.isPresent());
        assertEquals(latest.getBookingId(), result.get().getBookingId());
    }

    @Test
    public void testAssignDriver() {
        bookingService.createBooking(sampleBooking);
        List<BookingDTO> bookings = bookingService.getAllBookings();
        BookingDTO latest = bookings.get(bookings.size() - 1);

        // Assuming driverId 2 and assignedByUser 1 exist
        boolean result = bookingService.assignDriver(latest.getBookingId(), 1, 1);
        assertTrue(result);
    }

    @Test
    public void testUpdateBookingStatus() {
        bookingService.createBooking(sampleBooking);
        List<BookingDTO> bookings = bookingService.getAllBookings();
        BookingDTO latest = bookings.get(bookings.size() - 1);

        boolean result = bookingService.updateBookingStatus(latest.getBookingId(), "Confirmed");
        assertTrue(result);
    }

    @Test
    public void testCancelBooking() {
        bookingService.createBooking(sampleBooking);
        List<BookingDTO> bookings = bookingService.getAllBookings();
        BookingDTO latest = bookings.get(bookings.size() - 1);

        boolean result = bookingService.cancelBooking(latest.getBookingId());
        assertTrue(result);
    }

    @Test
    public void testCompleteBooking() {
        bookingService.createBooking(sampleBooking);
        List<BookingDTO> bookings = bookingService.getAllBookings();
        BookingDTO latest = bookings.get(bookings.size() - 1);

        boolean result = bookingService.completeBooking(latest.getBookingId());
        assertTrue(result);
    }

    @Test
    public void testGetCompletedBookings() {
        List<BookingDTO> completedBookings = bookingService.getCompletedBookings();
        assertNotNull(completedBookings);
    }

    @Test
    public void testGetPendingBookings() {
        List<BookingDTO> pending = bookingService.getPendingBookings();
        assertNotNull(pending);
    }

    @Test
    public void testCalculateFareWithPredefinedFare() {
        // Assuming predefined fare exists in DB for this route
        double fare = bookingService.calculateFare("Test Pickup", "Test Drop", 0);
        assertTrue(fare >= 0);
    }

    @Test
    public void testCalculateFareWithDistance() {
        // Assuming no predefined fare, fallback to farePerKm
        double fare = bookingService.calculateFare("Non-Defined Start", "Non-Defined End", 5.0);
        assertTrue(fare > 0);
    }

    @Test
    public void testGetBookingsByDriverId() {
        List<BookingDTO> bookings = bookingService.getBookingsByDriverId(2); // driverId = 2
        assertNotNull(bookings);
    }

    @Test
    public void testEndBookingByDriver() {
        bookingService.createBooking(sampleBooking);
        List<BookingDTO> bookings = bookingService.getAllBookings();
        BookingDTO latest = bookings.get(bookings.size() - 1);

        // Assign driver first
        bookingService.assignDriver(latest.getBookingId(), 2, 1);

        boolean result = bookingService.endBookingByDriver(latest.getBookingId(), 2);
        assertTrue(result);
    }

    @Test
    public void testGetCompletedBookingsByCustomer() {
        List<BookingDTO> result = bookingService.getCompletedBookingsByCustomer(1);
        assertNotNull(result);
    }
}
