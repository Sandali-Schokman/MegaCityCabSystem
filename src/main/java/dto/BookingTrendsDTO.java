package dto;

public class BookingTrendsDTO {
    private String date;
    private int totalBookings;
    private int completedBookings;
    private int cancelledBookings;

    public BookingTrendsDTO(String date, int totalBookings, int completedBookings, int cancelledBookings) {
        this.date = date;
        this.totalBookings = totalBookings;
        this.completedBookings = completedBookings;
        this.cancelledBookings = cancelledBookings;
    }

    public String getDate() { return date; }
    public int getTotalBookings() { return totalBookings; }
    public int getCompletedBookings() { return completedBookings; }
    public int getCancelledBookings() { return cancelledBookings; }
}
