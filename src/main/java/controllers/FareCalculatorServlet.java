package controllers;

import services.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculateFare")
public class FareCalculatorServlet extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pickup = request.getParameter("pickup");
        String dropoff = request.getParameter("dropoff");
        double distance = Double.parseDouble(request.getParameter("distance"));

        double fare = bookingService.calculateFare(pickup, dropoff, distance);

        // Set the fare as a request attribute
        request.setAttribute("calculatedFare", fare);

        // Forward the request to the booking.jsp page
        request.getRequestDispatcher("/views/booking.jsp").forward(request, response);
    }
}

