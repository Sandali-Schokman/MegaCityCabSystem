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
        try {
            // Retrieve parameters safely
            String pickup = request.getParameter("pickup");
            String dropoff = request.getParameter("dropoff");
            String distanceStr = request.getParameter("distance");

            // Validate input parameters
            if (pickup == null || pickup.trim().isEmpty() || dropoff == null || dropoff.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Pickup%20and%20Dropoff%20are%20required.");
                return;
            }

            double distance = 0;
            if (distanceStr != null && !distanceStr.trim().isEmpty()) {
                try {
                    distance = Double.parseDouble(distanceStr);
                    if (distance < 0) {
                        response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Distance%20cannot%20be%20negative.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=Invalid%20distance%20format.");
                    return;
                }
            }

            // Calculate the fare
            double fare = bookingService.calculateFare(pickup, dropoff, distance);

            // Retain input values for the form
            request.setAttribute("pickup", pickup);
            request.setAttribute("dropoff", dropoff);
            request.setAttribute("distance", distance);
            request.setAttribute("calculatedFare", fare);

            // Forward back to the booking page
            request.getRequestDispatcher("/views/customer/book-ride.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/views/customer/book-ride.jsp?error=An%20unexpected%20error%20occurred.");
        }
    }
}
