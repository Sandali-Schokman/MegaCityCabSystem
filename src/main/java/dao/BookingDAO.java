package dao;

import config.DatabaseConnection;
import dto.BookingDTO;
import dto.DriverDTO;
import mappers.BookingMapper;
import mappers.DriverMapper;
import models.Booking;
import models.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    //Add a new booking
    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (customer_id, driver_id, pickup_location, dropoff_location, scheduled_time, " +
                "booking_status, fare, payment_status, assigned_by, assigned_by_user, assigned_time, completion_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, booking.getCustomerId());
            stmt.setObject(2, booking.getDriverId(), Types.INTEGER);
            stmt.setString(3, booking.getPickupLocation());
            stmt.setString(4, booking.getDropoffLocation());
            stmt.setTimestamp(5, booking.getScheduledTime());
            stmt.setString(6, booking.getBookingStatus());
            stmt.setDouble(7, booking.getFare());
            stmt.setString(8, booking.getPaymentStatus());
            stmt.setString(9, booking.getAssignedBy());
            stmt.setObject(10, booking.getAssignedByUser(), Types.INTEGER);
            stmt.setTimestamp(11, booking.getAssignedTime());
            stmt.setTimestamp(12, booking.getCompletionTime());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Retrieve a booking by ID
    public BookingDTO getBookingById(int bookingId) {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return BookingMapper.toDTO(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Retrieve all bookings
    public List<BookingDTO> getAllBookings() {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings ORDER BY scheduled_time DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BookingDTO booking = BookingMapper.toDTO(mapResultSetToBooking(rs));
                System.out.println("DEBUG: Found Booking - ID: " + booking.getBookingId() + ", Status: " + booking.getBookingStatus());
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("DEBUG: Total Bookings Retrieved: " + bookings.size());
        return bookings;
    }

    //Retrieve bookings for a specific customer
    public List<BookingDTO> getBookingsByCustomer(int customerId) {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT b.*, p.payment_status AS PS FROM bookings b JOIN payments p ON b.booking_id = p.booking_id  WHERE customer_id = ? ORDER BY scheduled_time DESC";
        //SELECT p.* FROM payments p INNER JOIN bookings b ON p.booking_id = b.booking_id INNER JOIN drivers d ON d.driver_id WHERE d.user_id = ? AND p.method = 'CASH' AND p.verified_by_driver = 'NO';

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingDTO booking = BookingMapper.toDTO(mapResultSetToBooking(rs));
                booking.setPaymentStatus(rs.getString("PS"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    //Retrieve pending bookings that need driver assignment
    public List<BookingDTO> getPendingBookings() {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE booking_status = 'PENDING' ORDER BY scheduled_time ASC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(BookingMapper.toDTO(mapResultSetToBooking(rs)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<BookingDTO> getCompletedBookings() {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE booking_status = 'COMPLETED' ORDER BY scheduled_time ASC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(BookingMapper.toDTO(mapResultSetToBooking(rs)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    //Update booking status
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        String query = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, bookingId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Cancel a booking
    public boolean cancelBooking(int bookingId) {
        String query = "UPDATE bookings SET booking_status = 'CANCELLED' WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Mark a booking as completed
    public boolean completeBooking(int bookingId) {
        String query = "UPDATE bookings SET booking_status = 'COMPLETED', completion_time = CURRENT_TIMESTAMP WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Map ResultSet to Booking Object
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        return new Booking.BookingBuilder()
                .setBookingId(rs.getInt("booking_id"))
                .setCustomerId(rs.getInt("customer_id"))
                .setDriverId((Integer) rs.getObject("driver_id"))
                .setPickupLocation(rs.getString("pickup_location"))
                .setDropoffLocation(rs.getString("dropoff_location"))
                .setScheduledTime(rs.getTimestamp("scheduled_time"))
                .setBookingStatus(rs.getString("booking_status"))
                .setFare(rs.getDouble("fare"))
                .setPaymentStatus(rs.getString("payment_status"))
                .setAssignedBy(rs.getString("assigned_by"))
                .setAssignedByUser((Integer) rs.getObject("assigned_by_user"))
                .setAssignedTime(rs.getTimestamp("assigned_time"))
                .setCompletionTime(rs.getTimestamp("completion_time"))
                .build();
    }

    // Assign a driver to a booking manually
    public boolean assignDriver(int bookingId, int driverId, int assignedByUser) {
        String query = "UPDATE bookings SET driver_id = ?, booking_status = 'CONFIRMED', assigned_by = 'MANAGER', assigned_by_user = ?, assigned_time = NOW() WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, driverId);
            stmt.setInt(2, assignedByUser);
            stmt.setInt(3, bookingId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    // Get bookings by Driver ID
    public List<BookingDTO> getBookingsByDriverId(int driverId) {
        List<BookingDTO> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE driver_id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking.BookingBuilder()
                        .setBookingId(rs.getInt("booking_id"))
                        .setCustomerId(rs.getInt("customer_id"))
                        .setDriverId(rs.getInt("driver_id"))
                        .setPickupLocation(rs.getString("pickup_location"))
                        .setDropoffLocation(rs.getString("dropoff_location"))
                        .setScheduledTime(rs.getTimestamp("scheduled_time"))
                        .setDriverId(rs.getInt("driver_id"))
                        .build();
                bookings.add(BookingMapper.toDTO(booking));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean endBookingByDriver(int bookingId, int driverId) {
        String updateBooking = "UPDATE bookings SET booking_status=?, completion_time=NOW() " +
                "WHERE booking_id=?";
        String updateDriverRides = "UPDATE drivers SET completed_rides = completed_rides + 1 WHERE driver_id=?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement bookingStmt = conn.prepareStatement(updateBooking);
             PreparedStatement driverStmt = conn.prepareStatement(updateDriverRides)) {

            bookingStmt.setString(1,"COMPLETED");
            bookingStmt.setInt(2, bookingId);


            int updatedRows = bookingStmt.executeUpdate();
            System.out.println("database"+updatedRows);
            if (updatedRows > 0) {
                driverStmt.setInt(1, driverId);
                driverStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<BookingDTO> getCompletedBookingsByCustomer(int customerId) {
        List<BookingDTO> bookings = new ArrayList<>();

        String query = "SELECT * FROM bookings WHERE customer_id = ? AND booking_status = 'COMPLETED'";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Using Builder Pattern to build Booking entity
                Booking booking = new Booking.BookingBuilder()
                        .bookingId(rs.getInt("booking_id"))
                        .customerId(rs.getInt("customer_id"))
                        .driverId(rs.getInt("driver_id"))
                        .pickupLocation(rs.getString("pickup_location"))
                        .dropoffLocation(rs.getString("dropoff_location"))
                        .scheduledTime(rs.getTimestamp("scheduled_time"))
                        .bookingStatus(rs.getString("booking_status"))
                        .fare(rs.getDouble("fare"))
                        .paymentStatus(rs.getString("payment_status"))
                        .assignedTime(rs.getTimestamp("assigned_time"))
                        .completionTime(rs.getTimestamp("completion_time"))
                        .assignedBy(rs.getString("assigned_by"))
                        .assignedByUser(rs.getInt("assigned_by_user"))
                        .build();

                bookings.add(BookingMapper.toDTO(booking));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

}
