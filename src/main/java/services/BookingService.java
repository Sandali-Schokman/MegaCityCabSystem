package services;

import dao.BookingDAO;
import dto.BookingDTO;
import mappers.BookingMapper;
import models.Booking;


import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();

    public boolean createBooking(BookingDTO bookingDTO) {
        Booking booking = BookingMapper.toEntity(bookingDTO);
        return bookingDAO.addBooking(booking);
    }

    public List<BookingDTO> getAllBookings() {
        return bookingDAO.getAllBookings();
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

}

