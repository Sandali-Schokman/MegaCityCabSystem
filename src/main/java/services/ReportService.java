package services;

import dao.ReportDAO;
import dto.BookingTrendsDTO;
import dto.PerformanceReportDTO;

import java.util.List;

public class ReportService {
    private ReportDAO reportDAO = new ReportDAO(); // Dependency Injection

    public List<BookingTrendsDTO> getBookingTrends() {
        return reportDAO.getBookingTrends();
    }

    public List<PerformanceReportDTO> getDriverPerformance() {
        return reportDAO.getDriverPerformance();
    }
}
