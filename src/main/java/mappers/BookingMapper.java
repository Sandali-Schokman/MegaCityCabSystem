package mappers;

import dto.BookingDTO;
import models.Booking;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingId(),
                booking.getCustomerId(),
                booking.getDriverId(),
                booking.getPickupLocation(),
                booking.getDropoffLocation(),
                booking.getScheduledTime(),
                booking.getBookingStatus(),
                booking.getFare(),
                booking.getPaymentStatus(),
                booking.getAssignedTime(),
                booking.getCompletionTime()
        );
    }

    public static Booking toEntity(BookingDTO dto) {
        return new Booking(
                dto.getBookingId(),
                dto.getCustomerId(),
                dto.getDriverId(),
                dto.getPickupLocation(),
                dto.getDropoffLocation(),
                dto.getScheduledTime(),
                dto.getBookingStatus(),
                dto.getFare(),
                dto.getPaymentStatus(),
                dto.getAssignedTime(),
                dto.getCompletionTime()
        );
    }
}
