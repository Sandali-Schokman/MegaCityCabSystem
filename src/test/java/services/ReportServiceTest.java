package services;

import dto.BookingTrendsDTO;
import dto.PerformanceReportDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ReportServiceTest {

    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportService();
    }

    @After
    public void tearDown() {
        // Optional: cleanup if needed after each test
    }

    @Test
    public void testGetBookingTrends() {
        List<BookingTrendsDTO> bookingTrends = reportService.getBookingTrends();
        assertNotNull("Booking trends list should not be null", bookingTrends);

        // Optional: Check if there's at least one trend
        if (!bookingTrends.isEmpty()) {
            BookingTrendsDTO trend = bookingTrends.get(0);
            assertNotNull("Booking trend must have a date or category", trend.getDate());
            // You can add more specific field validations if needed
        }
    }

    @Test
    public void testGetDriverPerformance() {
        List<PerformanceReportDTO> performanceReports = reportService.getDriverPerformance();
        assertNotNull("Driver performance report list should not be null", performanceReports);

        // Optional: Check if one report contains expected structure
        if (!performanceReports.isEmpty()) {
            PerformanceReportDTO report = performanceReports.get(0);
            assertTrue("Driver ID should be a positive number", report.getDriverId() > 0);
            assertNotNull("Driver name should not be null", report.getDriverName());
        }
    }
}
