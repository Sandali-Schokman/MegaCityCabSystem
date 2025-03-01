package dao;

import config.DatabaseConnection;
import dto.BookingDTO;
import mappers.BookingMapper;
import models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (customer_id, pickup_location, dropoff_location, scheduled_time, fare) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setString(2, booking.getPickupLocation());
            stmt.setString(3, booking.getDropoffLocation());
            stmt.setTimestamp(4, booking.getScheduledTime());
            stmt.setDouble(5, booking.getFare());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BookingDTO> getAllBookings() {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getTimestamp("scheduled_time"),
                        rs.getString("booking_status"),
                        rs.getDouble("fare"),
                        rs.getString("payment_status"),
                        rs.getTimestamp("assigned_time"),
                        rs.getTimestamp("completion_time")
                );
                bookings.add(BookingMapper.toDTO(booking));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Get fare based on predefined distance
    public Double getPredefinedFare(String pickup, String dropoff) {
        String query = "SELECT total_fare FROM distance_rates WHERE pickup_location = ? AND dropoff_location = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pickup);
            stmt.setString(2, dropoff);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total_fare");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No predefined fare found
    }

    // Get per-kilometer rate for manual distance entry
    public Double getFarePerKm() {
        String query = "SELECT fare_per_km FROM distance_rates LIMIT 1";  // Assuming a global fare per km
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getDouble("fare_per_km");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 50.00; // Default per-km rate if not found
    }

    // Get pending bookings (bookings without assigned drivers)
    public List<BookingDTO> getPendingBookings() {
        List<BookingDTO> pendingBookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE driver_id IS NULL AND booking_status = 'PENDING'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("driver_id"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getTimestamp("scheduled_time"),
                        rs.getString("booking_status"),
                        rs.getDouble("fare"),
                        rs.getString("payment_status"),
                        rs.getTimestamp("assigned_time"),
                        rs.getTimestamp("completion_time")
                );
                pendingBookings.add(BookingMapper.toDTO(booking));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingBookings;
    }

    // Assign a driver to a booking manually
    public boolean assignDriverToBooking(int bookingId, int driverId, int assignedBy) {
        String query = "UPDATE bookings SET driver_id = ?, booking_status = 'CONFIRMED', assigned_by = 'MANAGER', assigned_by_user = ?, assigned_time = NOW() WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, driverId);
            stmt.setInt(2, assignedBy);
            stmt.setInt(3, bookingId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
