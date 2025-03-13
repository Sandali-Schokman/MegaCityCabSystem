package services;

import dao.BookingDAO;
import dto.BookingDTO;
import mappers.BookingMapper;
import models.Booking;
import java.util.List;
import java.util.Optional;

//Uses Facade Pattern to simplify booking operations.

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();

    //Create a new booking
    public boolean createBooking(BookingDTO bookingDTO) {
        Booking booking = BookingMapper.toEntity(bookingDTO);
        return bookingDAO.addBooking(booking);
    }

    //Get booking details by ID
    public Optional<BookingDTO> getBookingById(int bookingId) {
        return Optional.ofNullable(bookingDAO.getBookingById(bookingId));
    }

    //Get all bookings
    public List<BookingDTO> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    public List<BookingDTO> getCompletedBookings() {
        return bookingDAO.getCompletedBookings();
    }

    //Get bookings for a specific customer
    public List<BookingDTO> getBookingsByCustomer(int customerId) {
        return bookingDAO.getBookingsByCustomer(customerId);
    }

    //Get pending bookings for driver assignment
    public List<BookingDTO> getPendingBookings() {
        return bookingDAO.getPendingBookings();
    }

    //Assign a driver to a booking
    public boolean assignDriver(int bookingId, int driverId, int assignedByUser) {
        return bookingDAO.assignDriver(bookingId, driverId, assignedByUser);
    }

    //Update the status of a booking
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        return bookingDAO.updateBookingStatus(bookingId, newStatus);
    }

    public List<BookingDTO> getBookingsByDriverId(int driverId) {
        return bookingDAO.getBookingsByDriverId(driverId);
    }

    //Cancel a booking
    public boolean cancelBooking(int bookingId) {
        return bookingDAO.cancelBooking(bookingId);
    }

    //Mark a booking as completed
    public boolean completeBooking(int bookingId) {
        return bookingDAO.completeBooking(bookingId);
    }

    // Calculate fare
    public double calculateFare(String pickup, String dropoff, double distanceKm) {
        Double predefinedFare = bookingDAO.getPredefinedFare(pickup, dropoff);

        if (predefinedFare != null) {
            return predefinedFare; // Use predefined fare for fixed routes
        } else {
            Double farePerKm = bookingDAO.getFarePerKm();
            return distanceKm * farePerKm; // Calculate dynamically
        }
    }

    public boolean endBookingByDriver(int bookingId, int driverId) {
        return bookingDAO.endBookingByDriver(bookingId, driverId);
    }

    // Get completed bookings for a specific customer (used for complaints)
    public List<BookingDTO> getCompletedBookingsByCustomer(int customerId) {
        return bookingDAO.getCompletedBookingsByCustomer(customerId);
    }

}

