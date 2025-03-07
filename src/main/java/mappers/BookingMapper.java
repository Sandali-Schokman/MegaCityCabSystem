package mappers;

import dto.BookingDTO;
import models.Booking;

//BookingMapper - Converts between Booking Entity & BookingDTO.

public class BookingMapper {

    // Convert Entity to DTO
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
                booking.getAssignedBy(),
                booking.getAssignedByUser(),
                booking.getAssignedTime(),
                booking.getCompletionTime()
        );
    }

    // Convert DTO to Entity
    public static Booking toEntity(BookingDTO dto) {
        return new Booking.BookingBuilder()
                .setBookingId(dto.getBookingId())
                .setCustomerId(dto.getCustomerId())
                .setDriverId(dto.getDriverId())
                .setPickupLocation(dto.getPickupLocation())
                .setDropoffLocation(dto.getDropoffLocation())
                .setScheduledTime(dto.getScheduledTime())
                .setBookingStatus(dto.getBookingStatus())
                .setFare(dto.getFare())
                .setPaymentStatus(dto.getPaymentStatus())
                .setAssignedBy(dto.getAssignedBy())
                .setAssignedByUser(dto.getAssignedByUser())
                .setAssignedTime(dto.getAssignedTime())
                .setCompletionTime(dto.getCompletionTime())
                .build();
    }
}
